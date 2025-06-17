package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contenu;
    @Column(name="date_creation", nullable = false)
    private LocalDateTime dateCreation;
    @Column(name="date_modification")
    private LocalDateTime dateModification;

    @Column(name="nb_like", nullable = false)
    private int nbLike = 0;
    @Column(name="nb_dislike", nullable = false)
    private int nbDislike = 0;
    @Column(name="nb_signalement", nullable = false)
    private int nbSignalement = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_topic", nullable = false)
    private Topic topic;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_createur", nullable = false)
    private Utilisateur createur;

    @ManyToOne
    @JoinColumn(name= "id_modificateur")
    private Utilisateur modificateur;

    @OneToMany(mappedBy = "message")
    private Set<ReactionMessage> reactions = new HashSet<>();

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
     * @return dateModification
     */
    public LocalDateTime getDateModification() {
        return dateModification;
    }

    /**
     * Setter
     * @param dateModification sets value
     */
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
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
     * @return modificateur
     */
    public Utilisateur getModificateur() {
        return modificateur;
    }

    /**
     * Setter
     * @param modificateur sets value
     */
    public void setModificateur(Utilisateur modificateur) {
        this.modificateur = modificateur;
    }

    /**
     * Getter
     * @return reactions
     */
    public Set<ReactionMessage> getReactions() {
        return reactions;
    }

    /**
     * Setter
     * @param reactions sets value
     */
    public void setReactions(Set<ReactionMessage> reactions) {
        this.reactions = reactions;
    }
}
