package fr.diginamic.qualiair.entity;

import fr.diginamic.qualiair.entity.composite.UtilisateurRubrique;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rubrique_modification")
public class RubriqueModification
{
    @EmbeddedId
    private UtilisateurRubrique id;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    private String raison;
    private String commentaire;
    
    @MapsId("utilisateurId")
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
    
    @MapsId("rubriqueId")
    @ManyToOne
    @JoinColumn(name = "id_rubrique")
    private Rubrique rubrique;
    
    public RubriqueModification()
    {
    }
    
    /**
     * Getter
     * @return id
     */
    public UtilisateurRubrique getId()
    {
        return id;
    }
    
    /**
     * Getter
     * @return dateModification
     */
    public LocalDateTime getDateModification()
    {
        return dateModification;
    }
    
    /**
     * Setter
     * @param dateModification sets value
     */
    public void setDateModification(LocalDateTime dateModification)
    {
        this.dateModification = dateModification;
    }
    
    /**
     * Getter
     * @return raison
     */
    public String getRaison()
    {
        return raison;
    }
    
    /**
     * Setter
     * @param raison sets value
     */
    public void setRaison(String raison)
    {
        this.raison = raison;
    }
    
    /**
     * Getter
     * @return commentaire
     */
    public String getCommentaire()
    {
        return commentaire;
    }
    
    /**
     * Setter
     * @param commentaire sets value
     */
    public void setCommentaire(String commentaire)
    {
        this.commentaire = commentaire;
    }
    
    /**
     * Getter
     * @return utilisateur
     */
    public Utilisateur getUtilisateur()
    {
        return utilisateur;
    }
    
    /**
     * Setter
     * @param utilisateur sets value
     */
    public void setUtilisateur(Utilisateur utilisateur)
    {
        this.utilisateur = utilisateur;
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
}
