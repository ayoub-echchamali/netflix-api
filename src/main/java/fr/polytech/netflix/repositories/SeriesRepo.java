package fr.polytech.netflix.repositories;

import fr.polytech.netflix.entities.CommentsEntity;
import fr.polytech.netflix.entities.SeriesEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeriesRepo extends CrudRepository<SeriesEntity, Integer> {
    @Query(value = "SELECT * FROM series s ORDER BY RELEASE_DATE DESC", nativeQuery = true)
    List<SeriesEntity> findAllOrderByReleaseDate();
}

