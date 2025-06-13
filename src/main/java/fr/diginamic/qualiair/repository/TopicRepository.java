package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
