package fr.diginamic.qualiair.entities;

import fr.diginamic.qualiair.entities.composite.UtilisateurRubrique;
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
}
