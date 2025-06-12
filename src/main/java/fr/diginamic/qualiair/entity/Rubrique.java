package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rubrique")
public class Rubrique
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    private String description;
    @Column(name = "priorite_affichage_indice", nullable = false)
    private int prioriteAffichageIndice = 0;
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_createur", nullable = false)
    private Utilisateur createur;

    @ManyToOne
    @JoinColumn(name= "id_modificateur")
    private Utilisateur modificateur;

    @OneToMany(mappedBy = "rubrique")
    private Set<Topic> topics = new HashSet<>();
    
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
}
