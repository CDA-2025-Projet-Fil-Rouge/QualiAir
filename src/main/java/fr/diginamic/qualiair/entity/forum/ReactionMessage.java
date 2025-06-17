package fr.diginamic.qualiair.entity.forum;

import fr.diginamic.qualiair.entity.Utilisateur;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Classe permettant de relier un utilisateur à une réaction
 * (like, dislike ou signalement) concernant un message du forum
 */
@Entity
@Table( name = "reaction_message",
        uniqueConstraints = @UniqueConstraint(columnNames = {"utilisateur_id", "message_id", "type"})
)
public class ReactionMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Utilisateur utilisateur;

    @ManyToOne(optional = false)
    private Message message;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    private LocalDateTime date;

    public ReactionMessage() {
    }

    /**
     * Getter
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter
     * @return utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * Setter
     * @param utilisateur utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Getter
     * @return message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Setter
     * @param message message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     * Getter
     * @return type
     */
    public ReactionType getType() {
        return type;
    }

    /**
     * Setter
     * @param type type
     */
    public void setType(ReactionType type) {
        this.type = type;
    }

    /**
     * Getter
     * @return date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Setter
     * @param date date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
