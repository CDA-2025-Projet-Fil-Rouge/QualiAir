package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.forum.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTopicId(Long idTopic);

    long countByTopicId(Long idTopic);

    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.reactions WHERE m.id = :id")
    Optional<Message> findWithReactionsById(@Param("id") Long id);

}
