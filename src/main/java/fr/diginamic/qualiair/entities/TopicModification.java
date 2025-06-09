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
}
