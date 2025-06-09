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
     * @return contenu
     */
    public String getContenu()
    {
        return contenu;
    }
    
    /**
     * Setter
     * @param contenu sets value
     */
    public void setContenu(String contenu)
    {
        this.contenu = contenu;
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
     * @return nbLike
     */
    public int getNbLike()
    {
        return nbLike;
    }
    
    /**
     * Setter
     * @param nbLike sets value
     */
    public void setNbLike(int nbLike)
    {
        this.nbLike = nbLike;
    }
    
    /**
     * Getter
     * @return nbDislike
     */
    public int getNbDislike()
    {
        return nbDislike;
    }
    
    /**
     * Setter
     * @param nbDislike sets value
     */
    public void setNbDislike(int nbDislike)
    {
        this.nbDislike = nbDislike;
    }
    
    /**
     * Getter
     * @return nbSignalement
     */
    public int getNbSignalement()
    {
        return nbSignalement;
    }
    
    /**
     * Setter
     * @param nbSignalement sets value
     */
    public void setNbSignalement(int nbSignalement)
    {
        this.nbSignalement = nbSignalement;
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
     * @return messageModifications
     */
    public Set<MessageModification> getMessageModifications()
    {
        return messageModifications;
    }
    
    /**
     * Setter
     * @param messageModifications sets value
     */
    public void setMessageModifications(Set<MessageModification> messageModifications)
    {
        this.messageModifications = messageModifications;
    }
}
