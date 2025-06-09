package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "topic")
public class Topic
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne
    @JoinColumn(name = "id_createur")
    private Utilisateur createur;
    @ManyToOne
    @JoinColumn(name = "id_rubrique")
    private Rubrique rubrique;
    
    @OneToMany(mappedBy = "topic")
    private Set<TopicModification> topicModifications;
    
    @OneToMany(mappedBy = "topic")
    private Set<Message> messages;
    
    public Topic()
    {
    }
}
