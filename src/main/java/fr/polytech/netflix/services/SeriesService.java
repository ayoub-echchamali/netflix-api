package fr.polytech.netflix.services;

import fr.polytech.netflix.dtos.CommentsDto;
import fr.polytech.netflix.dtos.SeriesDto;
import fr.polytech.netflix.entities.SeriesEntity;
import fr.polytech.netflix.exceptions.ResourceNotFoundException;
import fr.polytech.netflix.repositories.SeriesRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SeriesService {
    private final SeriesRepo seriesRepo;
    private final MinioService minioService;
    private final Environment env;

    public SeriesService(SeriesRepo seriesRepo, MinioService minioService, Environment environment) {
        this.seriesRepo = seriesRepo;
        this.minioService = minioService;
        this.env = environment;
    }

    public SeriesEntity getSeries(Integer id){
        return seriesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Series with id: " + id + " not found."));
    }

    public List<SeriesEntity> getAllSeries(){
        return (List<SeriesEntity>) seriesRepo.findAllOrderByReleaseDate();
    }

    public void createSeries(SeriesDto series){
        try{
            seriesRepo.save(SeriesEntity
                .builder()
                .name(series.getName())
                .releaseDate(series.getReleaseDate())
                .description(series.getDescription())
                .cover(series.getCover())
                .comments(series.getComments().stream().map(CommentsDto::toEntity).toList())
                .build());
        } catch (Exception e){
            log.error("Error while adding series: " + e);
        }
    }

    public void deleteSeries(Integer id){
        try{
            seriesRepo.deleteById(id);
        } catch (Exception e){
            log.error("Error deleting series with id(" + id + "): " + e);
        }
    }

    public String getUploadLink(Integer id){
        Optional<SeriesEntity> res = seriesRepo.findById(id);
        if (res.isPresent()){
            SeriesEntity series = res.get();
            series.setCover(id.toString());
        }
        return minioService.getUploadUrl(id.toString(), env.getProperty("s3.buckets.exocover"));
    }

    public String getDownloadLink(Integer id){
        return minioService.getDownloadUrl(id.toString(), env.getProperty("s3.buckets.exocover"));
    }
}
