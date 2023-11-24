package fr.polytech.netflix.repositories;

import fr.polytech.netflix.entities.CommentsEntity;
import fr.polytech.netflix.entities.SeriesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentsRepo extends CrudRepository<CommentsEntity, Integer> {
    List<CommentsEntity> findAllBySeries(SeriesEntity series);
}
