package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "utilisateur")
public class Utilisateur
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String prenom;
    private String nom;
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;
    private String email;
    private RoleUtilisateur role;
    
    @ManyToOne
    @JoinColumn(name = "id_adresse")
    private Adresse adresse;
    
    @OneToMany(mappedBy = "createur")
    private Set<Message> messages;
    @OneToMany(mappedBy = "createur")
    private Set<Topic> topics;
    @OneToMany(mappedBy = "createur")
    private Set<Rubrique> rubriques;
    
    @OneToMany(mappedBy = "utilisateur")
    private Set<MessageModification> messageModifications;
    @OneToMany(mappedBy = "utilisateur")
    private Set<TopicModification> topicModifications;
    @OneToMany(mappedBy = "utilisateur")
    private Set<RubriqueModification> rubriqueModifications;
    
    public Utilisateur()
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
     * @return prenom
     */
    public String getPrenom()
    {
        return prenom;
    }
    
    /**
     * Setter
     * @param prenom sets value
     */
    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
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
     * @return dateNaissance
     */
    public LocalDate getDateNaissance()
    {
        return dateNaissance;
    }
    
    /**
     * Setter
     * @param dateNaissance sets value
     */
    public void setDateNaissance(LocalDate dateNaissance)
    {
        this.dateNaissance = dateNaissance;
    }
    
    /**
     * Getter
     * @return dateInscription
     */
    public LocalDateTime getDateInscription()
    {
        return dateInscription;
    }
    
    /**
     * Setter
     * @param dateInscription sets value
     */
    public void setDateInscription(LocalDateTime dateInscription)
    {
        this.dateInscription = dateInscription;
    }
    
    /**
     * Getter
     * @return email
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * Setter
     * @param email sets value
     */
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    /**
     * Getter
     * @return role
     */
    public RoleUtilisateur getRole()
    {
        return role;
    }
    
    /**
     * Setter
     * @param role sets value
     */
    public void setRole(RoleUtilisateur role)
    {
        this.role = role;
    }
    
    /**
     * Getter
     * @return adresse
     */
    public Adresse getAdresse()
    {
        return adresse;
    }
    
    /**
     * Setter
     * @param adresse sets value
     */
    public void setAdresse(Adresse adresse)
    {
        this.adresse = adresse;
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
    
    /**
     * Getter
     * @return topics
     */
    public Set<Topic> getTopics()
    {
        return topics;
    }
    
    /**
     * Setter
     * @param topics sets value
     */
    public void setTopics(Set<Topic> topics)
    {
        this.topics = topics;
    }
    
    /**
     * Getter
     * @return rubriques
     */
    public Set<Rubrique> getRubriques()
    {
        return rubriques;
    }
    
    /**
     * Setter
     * @param rubriques sets value
     */
    public void setRubriques(Set<Rubrique> rubriques)
    {
        this.rubriques = rubriques;
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
     * @return rubriqueModifications
     */
    public Set<RubriqueModification> getRubriqueModifications()
    {
        return rubriqueModifications;
    }
    
    /**
     * Setter
     * @param rubriqueModifications sets value
     */
    public void setRubriqueModifications(Set<RubriqueModification> rubriqueModifications)
    {
        this.rubriqueModifications = rubriqueModifications;
    }
}
