package fr.diginamic.qualiair.dto.forumDto;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;

import java.time.LocalDateTime;

/**
 * DTO représentant un topic créé dans une rubrique du forum.
 */
public class TopicDto {
    private Long id;
    private String nom;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private UtilisateurDto createur;
    private UtilisateurDto modificateur;
    private Long idRubrique;

    public TopicDto() {
    }

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
     *
     * @return createur
     */
    public UtilisateurDto getCreateur() {
        return createur;
    }

    /**
     * Setter
     *
     * @param createur createur
     */
    public void setCreateur(UtilisateurDto createur) {
        this.createur = createur;
    }

    /**
     * Getter
     *
     * @return modificateur
     */
    public UtilisateurDto getModificateur() {
        return modificateur;
    }

    /**
     * Setter
     *
     * @param modificateur modificateur
     */
    public void setModificateur(UtilisateurDto modificateur) {
        this.modificateur = modificateur;
    }

    /**
     * Getter
     * @return idRubrique
     */
    public Long getIdRubrique() {
        return idRubrique;
    }

    /**
     * Setter
     * @param idRubrique sets value
     */
    public void setIdRubrique(Long idRubrique) {
        this.idRubrique = idRubrique;
    }
}
