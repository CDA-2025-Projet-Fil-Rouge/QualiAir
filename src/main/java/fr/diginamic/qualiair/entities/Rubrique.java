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
     * @return description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Setter
     * @param description sets value
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * Getter
     * @return prioriteAffichageIndice
     */
    public int getPrioriteAffichageIndice()
    {
        return prioriteAffichageIndice;
    }
    
    /**
     * Setter
     * @param prioriteAffichageIndice sets value
     */
    public void setPrioriteAffichageIndice(int prioriteAffichageIndice)
    {
        this.prioriteAffichageIndice = prioriteAffichageIndice;
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
