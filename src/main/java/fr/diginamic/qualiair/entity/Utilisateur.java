package fr.diginamic.qualiair.entity;

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
    private RoleUtilisateur role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_adresse", nullable = false)
    private Adresse adresse;
    
    @OneToMany(mappedBy = "createur")
    private Set<Message> messages;
    @OneToMany(mappedBy = "createur")
    private Set<Topic> topics;
    @OneToMany(mappedBy = "createur")
    private Set<Rubrique> rubriques;
    

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
}
