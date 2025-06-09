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
}
