package fr.diginamic.qualiair.entity.forum;

import fr.diginamic.qualiair.entity.Utilisateur;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topic")
public class Topic
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_createur", nullable = false)
    private Utilisateur createur;

    @ManyToOne
    @JoinColumn(name= "id_modificateur")
    private Utilisateur modificateur;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rubrique", nullable = false)
    private Rubrique rubrique;
    
    @OneToMany(mappedBy = "topic")
    private Set<Message> messages = new HashSet<>();

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
     * @return dateModification
     */
    public LocalDateTime getDateModification() {
        return dateModification;
    }

    /**
     * Setter
     *
     * @param dateModification sets value
     */
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
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
     *
     * @param modificateur sets value
     */
    public void setModificateur(Utilisateur modificateur) {
        this.modificateur = modificateur;
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
