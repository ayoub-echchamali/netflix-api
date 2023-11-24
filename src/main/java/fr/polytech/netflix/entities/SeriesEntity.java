package fr.polytech.netflix.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "series")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SeriesEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String name;

    @Column(name = "cover", columnDefinition = "varchar(255)", nullable = true)
    private String cover;

    @Column(name = "description", columnDefinition = "varchar(255)", nullable = true)
    private String description;

    @OneToMany
    @JoinColumn(name = "comments")
    private List<CommentsEntity> comments;

    @Column(name = "release_date", columnDefinition = "date", nullable = false)
    private LocalDate releaseDate;

}
