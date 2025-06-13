package fr.diginamic.qualiair.dto.forumDto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class MessageDto {

    private Long id;
    private String contenu;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private int nbLike;
    private int nbDislike;
    private int nbSignalement;
    private Long idCreateur;
    private Long idModificateur;
    private Long idTopic;

    public MessageDto() {
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
     * @return contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Setter
     * @param contenu sets value
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
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
     * @return nbLike
     */
    public int getNbLike() {
        return nbLike;
    }

    /**
     * Setter
     * @param nbLike sets value
     */
    public void setNbLike(int nbLike) {
        this.nbLike = nbLike;
    }

    /**
     * Getter
     * @return nbDislike
     */
    public int getNbDislike() {
        return nbDislike;
    }

    /**
     * Setter
     * @param nbDislike sets value
     */
    public void setNbDislike(int nbDislike) {
        this.nbDislike = nbDislike;
    }

    /**
     * Getter
     * @return nbSignalement
     */
    public int getNbSignalement() {
        return nbSignalement;
    }

    /**
     * Setter
     * @param nbSignalement sets value
     */
    public void setNbSignalement(int nbSignalement) {
        this.nbSignalement = nbSignalement;
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

    /**
     * Getter
     * @return idTopic
     */
    public Long getIdTopic() {
        return idTopic;
    }

    /**
     * Setter
     * @param idTopic sets value
     */
    public void setIdTopic(Long idTopic) {
        this.idTopic = idTopic;
    }
}
