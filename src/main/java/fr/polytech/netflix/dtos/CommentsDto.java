package fr.polytech.netflix.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.polytech.netflix.entities.CommentsEntity;
import fr.polytech.netflix.entities.SeriesEntity;
import fr.polytech.netflix.repositories.SeriesRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class CommentsDto {

    private static SeriesRepo seriesRepo;

    @JsonProperty("author")
    private String author;

    @JsonProperty("score")
    private Float score;

    @JsonProperty("content")
    private String content;

    @JsonProperty("screenshot")
    private String screenshot;

    @JsonProperty("series_id")
    private Integer series_id;

    public static CommentsDto toDto(CommentsEntity comment) {
        return CommentsDto.builder()
                .author(comment.getAuthor())
                .content(comment.getContent())
                .score(comment.getScore())
                .screenshot(comment.getScreenshot())
                .build();
    }

    public static CommentsEntity toEntity(CommentsDto comment) {
        return CommentsEntity.builder()
                .author(comment.getAuthor())
                .content(comment.getContent())
                .score(comment.getScore())
                .screenshot(comment.getScreenshot())
                .series(seriesRepo.findById(comment.getSeries_id()).get())
                .build();
    }

}
