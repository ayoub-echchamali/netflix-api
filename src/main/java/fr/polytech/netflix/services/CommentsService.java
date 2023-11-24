package fr.polytech.netflix.services;

import fr.polytech.netflix.dtos.CommentsDto;
import fr.polytech.netflix.entities.CommentsEntity;
import fr.polytech.netflix.entities.SeriesEntity;
import fr.polytech.netflix.exceptions.ResourceNotFoundException;
import fr.polytech.netflix.repositories.CommentsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentsService {

    private final CommentsRepo commentsRepo;
    private final MinioService minioService;
    private final Environment env;

    public CommentsService(CommentsRepo commentsRepo, MinioService minioService, Environment environment) {
        this.commentsRepo = commentsRepo;
        this.minioService = minioService;
        this.env = environment;
    }

    public List<CommentsEntity> getAllComments(SeriesEntity series){
        try {
            return (List<CommentsEntity>) commentsRepo.findAllBySeries(series);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Comments for series \"" + series.getName() + "\" not found.");
        }
    }

    public void createComments(CommentsDto comments, SeriesEntity series){
        try{
            commentsRepo.save(CommentsEntity
                .builder()
                .author(comments.getAuthor())
                .score(comments.getScore())
                .screenshot(comments.getScreenshot())
                .content(comments.getContent())
                .series(series)
                .build());
        } catch (Exception e){
            log.error("Error while adding comment: " + e);
        }
    }

    public String getUploadLink(Integer series_id, Integer comment_id){
        String fileName = series_id.toString() + "_" + comment_id.toString();
        // update DB filename
        Optional<CommentsEntity> res = commentsRepo.findById(comment_id);
        if (res.isPresent()){
            CommentsEntity comment = res.get();
            comment.setScreenshot(fileName);
        }
        return minioService.getUploadUrl(fileName, env.getProperty("s3.buckets.exoscreenshot"));
    }

    public String getDownloadLink(Integer series_id, Integer comment_id){
        String fileName = series_id.toString() + "_" + comment_id.toString();
        return minioService.getDownloadUrl(fileName, env.getProperty("s3.buckets.exoscreenshot"));
    }

}
