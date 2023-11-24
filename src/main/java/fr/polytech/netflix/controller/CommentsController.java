package fr.polytech.netflix.controller;

import fr.polytech.netflix.dtos.CommentsDto;
import fr.polytech.netflix.entities.SeriesEntity;
import fr.polytech.netflix.services.CommentsService;
import fr.polytech.netflix.services.CommentsService;
import fr.polytech.netflix.services.SeriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentsController {

    private final CommentsService commentsService;
    private final SeriesService seriesService;

    @GetMapping("/series/{series_id}/comments")
    public @ResponseBody List<CommentsDto> getComments(@PathVariable Integer series_id){
        log.info("Get: all comments");
        final SeriesEntity series = seriesService.getSeries(series_id);
        return commentsService.getAllComments(series).stream()
                .map(CommentsDto::toDto)
                .toList();
    }

    @PostMapping("/series/comments")
    public void addComments(@RequestBody CommentsDto comments){
        log.info("Post: adding new comment for id: " + comments.getAuthor());
        final SeriesEntity series = seriesService.getSeries(comments.getSeries_id());
        commentsService.createComments(comments, series);
    }

    @GetMapping("/series/{series_id}/comments/{comment_id}/screenshot/upload")
    public String getLinkUploadScreenshot(@PathVariable Integer series_id, @PathVariable Integer comment_id){
        log.info("Get: upload link for screenshot for comment with id (" + comment_id + " for series with id: " + series_id);
        return commentsService.getUploadLink(series_id, comment_id);
    }

    @GetMapping("/series/{series_id}/comments/{comment_id}/screenshot/download")
    public String getLinkDownloadScreenshot(@PathVariable Integer series_id, @PathVariable Integer comment_id){
        log.info("Put: download link for screenshot for comment with id (" + comment_id + " for series with id: " + series_id);
        return commentsService.getDownloadLink(series_id, comment_id);
    }


}
