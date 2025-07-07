package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionMessage;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReactionMessageRepository extends JpaRepository<ReactionMessage, Long> {

    boolean existsByUtilisateurAndMessageAndType(
            Utilisateur utilisateur, Message message, ReactionType type);

    Optional<ReactionMessage> findByUtilisateurAndMessageAndType(
            Utilisateur utilisateur, Message message, ReactionType type);
}
