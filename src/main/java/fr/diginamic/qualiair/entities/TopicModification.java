package fr.diginamic.qualiair.entities;

import fr.diginamic.qualiair.entities.composite.UtilisateurTopic;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topic_modification")
public class TopicModification
{
    @EmbeddedId
    private UtilisateurTopic id;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    private String raison;
    private String commentaire;
    
    @MapsId("utilisateurId")
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
    
    @MapsId("topicId")
    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;
    
    public TopicModification()
    {
    }
    
    /**
     * Getter
     * @return id
     */
    public UtilisateurTopic getId()
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
     * @return topic
     */
    public Topic getTopic()
    {
        return topic;
    }
    
    /**
     * Setter
     * @param topic sets value
     */
    public void setTopic(Topic topic)
    {
        this.topic = topic;
    }
}
