package fr.polytech.netflix.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "COMMENTS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "author", columnDefinition = "varchar(255)", nullable = false)
    private String author;

    @Column(name = "score", columnDefinition = "float", nullable = false)
    private Float score;

    @Column(name = "content", columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "screenshot", columnDefinition = "varchar(255)", nullable = false)
    private String screenshot;

    @ManyToOne()
    @JoinColumn(name="series")
    private SeriesEntity series;

}
