package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String contenu;
    private LocalDateTime dateCreation;
    private int nbLike;
    private int nbDislike;
    private int nbSignalement;
    
    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;
    @ManyToOne
    @JoinColumn(name = "id_createur")
    private Utilisateur createur;
    
    @OneToMany(mappedBy = "message")
    private Set<MessageModification> messageModifications;
    
    public Message()
    {
    }
}
