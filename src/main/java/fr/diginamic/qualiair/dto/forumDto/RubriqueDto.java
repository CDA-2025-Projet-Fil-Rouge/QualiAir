package fr.diginamic.qualiair.dto.forumDto;

import java.time.LocalDateTime;

/**
 * DTO représentant une rubrique du forum.
 */
public class RubriqueDto {
    private Long id;
    private String nom;
    private String description;
    private int prioriteAffichageIndice;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private Long idCreateur;
    private Long idModificateur;

    public RubriqueDto() {}

    /**
     * Getter
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter
     * @param id sets value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     * @param nom sets value
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     * @param description sets value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     * @return prioriteAffichageIndice
     */
    public int getPrioriteAffichageIndice() {
        return prioriteAffichageIndice;
    }

    /**
     * Setter
     * @param prioriteAffichageIndice sets value
     */
    public void setPrioriteAffichageIndice(int prioriteAffichageIndice) {
        this.prioriteAffichageIndice = prioriteAffichageIndice;
    }

    /**
     * Getter
     * @return dateCreation
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    /**
     * Setter
     * @param dateCreation sets value
     */
    public void setDateCreation(LocalDateTime dateCreation) {
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
     * @param dateModification sets value
     */
    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    /**
     * Getter
     * @return idCreateur
     */
    public Long getIdCreateur() {
        return idCreateur;
    }

    /**
     * Setter
     * @param idCreateur sets value
     */
    public void setIdCreateur(Long idCreateur) {
        this.idCreateur = idCreateur;
    }

    /**
     * Getter
     * @return idModificateur
     */
    public Long getIdModificateur() {
        return idModificateur;
    }

    /**
     * Setter
     * @param idModificateur sets value
     */
    public void setIdModificateur(Long idModificateur) {
        this.idModificateur = idModificateur;
    }
}
