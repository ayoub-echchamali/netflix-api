package fr.polytech.netflix.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.polytech.netflix.entities.SeriesEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SeriesDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("cover")
    private String cover;

    @JsonProperty("comments")
    private List<CommentsDto> comments;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    public static SeriesDto toDto(SeriesEntity series) {
        return SeriesDto.builder()
                .name(series.getName())
                .cover(series.getCover())
                .description(series.getDescription())
                .releaseDate(series.getReleaseDate())
                .comments(series.getComments().stream().map(CommentsDto::toDto).toList())
                .build();
    }
    public static SeriesEntity toEntity(SeriesDto series) {
        return SeriesEntity.builder()
                .name(series.getName())
                .cover(series.getCover())
                .comments(series.getComments().stream().map(CommentsDto::toEntity).toList())
                .releaseDate(series.getReleaseDate())
                .build();
    }

}
