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
    
    /**
     * Getter
     * @return id
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Getter
     * @return nom
     */
    public String getNom()
    {
        return nom;
    }
    
    /**
     * Setter
     * @param nom sets value
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    
    /**
     * Getter
     * @return dateCreation
     */
    public LocalDateTime getDateCreation()
    {
        return dateCreation;
    }
    
    /**
     * Setter
     * @param dateCreation sets value
     */
    public void setDateCreation(LocalDateTime dateCreation)
    {
        this.dateCreation = dateCreation;
    }
    
    /**
     * Getter
     * @return createur
     */
    public Utilisateur getCreateur()
    {
        return createur;
    }
    
    /**
     * Setter
     * @param createur sets value
     */
    public void setCreateur(Utilisateur createur)
    {
        this.createur = createur;
    }
    
    /**
     * Getter
     * @return rubrique
     */
    public Rubrique getRubrique()
    {
        return rubrique;
    }
    
    /**
     * Setter
     * @param rubrique sets value
     */
    public void setRubrique(Rubrique rubrique)
    {
        this.rubrique = rubrique;
    }
    
    /**
     * Getter
     * @return topicModifications
     */
    public Set<TopicModification> getTopicModifications()
    {
        return topicModifications;
    }
    
    /**
     * Setter
     * @param topicModifications sets value
     */
    public void setTopicModifications(Set<TopicModification> topicModifications)
    {
        this.topicModifications = topicModifications;
    }
    
    /**
     * Getter
     * @return messages
     */
    public Set<Message> getMessages()
    {
        return messages;
    }
    
    /**
     * Setter
     * @param messages sets value
     */
    public void setMessages(Set<Message> messages)
    {
        this.messages = messages;
    }
}
