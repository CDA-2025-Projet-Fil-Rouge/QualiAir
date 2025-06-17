package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.forum.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByRubriqueId(Long idRubrique);

    long countByRubriqueId(Long idRubrique);
}
