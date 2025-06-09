package fr.diginamic.qualiair.entities;

import fr.diginamic.qualiair.entities.composite.UtilisateurMessage;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_modification")
public class MessageModification
{
    @EmbeddedId
    private UtilisateurMessage id;
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    private String raison;
    private String commentaire;
    
    @MapsId("utilisateurId")
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
    
    @MapsId("messageId")
    @ManyToOne
    @JoinColumn(name = "id_message")
    private Message message;
    
    public MessageModification()
    {
    }
    
    /**
     * Getter
     * @return id
     */
    public UtilisateurMessage getId()
    {
        return id;
    }
    
    /**
     * Getter
     * @return dateModification
     */
    public LocalDateTime getDateModification()
    {
        return dateModification;
    }
    
    /**
     * Setter
     * @param dateModification sets value
     */
    public void setDateModification(LocalDateTime dateModification)
    {
        this.dateModification = dateModification;
    }
    
    /**
     * Getter
     * @return raison
     */
    public String getRaison()
    {
        return raison;
    }
    
    /**
     * Setter
     * @param raison sets value
     */
    public void setRaison(String raison)
    {
        this.raison = raison;
    }
    
    /**
     * Getter
     * @return commentaire
     */
    public String getCommentaire()
    {
        return commentaire;
    }
    
    /**
     * Setter
     * @param commentaire sets value
     */
    public void setCommentaire(String commentaire)
    {
        this.commentaire = commentaire;
    }
    
    /**
     * Getter
     * @return utilisateur
     */
    public Utilisateur getUtilisateur()
    {
        return utilisateur;
    }
    
    /**
     * Setter
     * @param utilisateur sets value
     */
    public void setUtilisateur(Utilisateur utilisateur)
    {
        this.utilisateur = utilisateur;
    }
    
    /**
     * Getter
     * @return message
     */
    public Message getMessage()
    {
        return message;
    }
    
    /**
     * Setter
     * @param message sets value
     */
    public void setMessage(Message message)
    {
        this.message = message;
    }
}
