package fr.diginamic.qualiair.entity;

import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionMessage;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.entity.forum.Topic;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String nom;
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    @Column(name = "date_inscription")
    private LocalDateTime dateInscription;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role = RoleUtilisateur.UTILISATEUR;


    @ManyToOne(optional = false)
    @JoinColumn(name = "id_adresse", nullable = false)
    private Adresse adresse;

    @OneToMany(mappedBy = "createur")
    private Set<Message> messages = new HashSet<>();
    @OneToMany(mappedBy = "createur")
    private Set<Topic> topics = new HashSet<>();
    @OneToMany(mappedBy = "createur")
    private Set<Rubrique> rubriques = new HashSet<>();

    @OneToMany(mappedBy = "modificateur")
    private Set<Message> messagesModifies = new HashSet<>();
    @OneToMany(mappedBy = "modificateur")
    private Set<Topic> topicsModifies = new HashSet<>();
    @OneToMany(mappedBy = "modificateur")
    private Set<Rubrique> rubriquesModifiees = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur")
    private Set<ReactionMessage> reactions = new HashSet<>();


    public Utilisateur()
    {

    }

    /**
     * Getter
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter
     *
     * @return prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter
     *
     * @param prenom sets value
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     *
     * @param nom sets value
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     *
     * @return dateNaissance
     */
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Setter
     *
     * @param dateNaissance sets value
     */
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Getter
     *
     * @return dateInscription
     */
    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    /**
     * Setter
     *
     * @param dateInscription sets value
     */
    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     * Getter
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     *
     * @param email sets value
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Getter
     * @return motDePasse
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Setter
     * @param motDePasse sets value
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
  
    /**
     * Getter
     *
     * @return role
     */
    public RoleUtilisateur getRole() {
        return role;
    }

    /**
     * Setter
     *
     * @param role sets value
     */
    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }

    /**
     * Getter
     *
     * @return adresse
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * Setter
     *
     * @param adresse sets value
     */
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter
     *
     * @return messages
     */
    public Set<Message> getMessages() {
        return messages;
    }

    /**
     * Setter
     *
     * @param messages sets value
     */
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    /**
     * Getter
     *
     * @return topics
     */
    public Set<Topic> getTopics() {
        return topics;
    }

    /**
     * Setter
     *
     * @param topics sets value
     */
    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    /**
     * Getter
     *
     * @return rubriques
     */
    public Set<Rubrique> getRubriques() {
        return rubriques;
    }

    /**
     * Setter
     *
     * @param rubriques sets value
     */
    public void setRubriques(Set<Rubrique> rubriques) {
        this.rubriques = rubriques;
    }

    /**
     * Getter
     * @return messagesModifies
     */
    public Set<Message> getMessagesModifies() {
        return messagesModifies;
    }

    /**
     * Setter
     * @param messagesModifies sets value
     */
    public void setMessagesModifies(Set<Message> messagesModifies) {
        this.messagesModifies = messagesModifies;
    }

    /**
     * Getter
     * @return topicsModifies
     */
    public Set<Topic> getTopicsModifies() {
        return topicsModifies;
    }

    /**
     * Setter
     * @param topicsModifies sets value
     */
    public void setTopicsModifies(Set<Topic> topicsModifies) {
        this.topicsModifies = topicsModifies;
    }

    /**
     * Getter
     * @return rubriquesModifiees
     */
    public Set<Rubrique> getRubriquesModifiees() {
        return rubriquesModifiees;
    }

    /**
     * Setter
     * @param rubriquesModifiees sets value
     */
    public void setRubriquesModifiees(Set<Rubrique> rubriquesModifiees) {
        this.rubriquesModifiees = rubriquesModifiees;
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
