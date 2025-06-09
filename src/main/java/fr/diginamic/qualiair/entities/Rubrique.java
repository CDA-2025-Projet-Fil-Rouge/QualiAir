package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "rubrique")
public class Rubrique
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String description;
    @Column(name = "priorite_affichage_indice")
    private int prioriteAffichageIndice;
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @ManyToOne
    @JoinColumn(name = "id_createur")
    private Utilisateur createur;
    
    @ManyToOne
    @JoinColumn(name = "id_rubrique")
    private Rubrique rubrique;
    
    @OneToMany(mappedBy = "rubrique")
    private Set<RubriqueModification> rubriqueModifications;
    
    public Rubrique()
    {
    }
}
