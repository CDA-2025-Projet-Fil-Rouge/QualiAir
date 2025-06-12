package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
