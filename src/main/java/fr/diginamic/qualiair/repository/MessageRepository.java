package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.forum.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTopicId(Long idTopic);

    long countByTopicId(Long idTopic);
}
