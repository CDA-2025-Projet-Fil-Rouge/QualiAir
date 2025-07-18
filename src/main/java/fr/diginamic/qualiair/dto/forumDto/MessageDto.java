package fr.diginamic.qualiair.dto.forumDto;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.forum.ReactionType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO représentant un message posté dans un topic du forum.
 */
public class MessageDto {

    private Long id;
    private String contenu;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private int nbLike;
    private int nbDislike;
    private int nbSignalement;
    private UtilisateurDto createur;
    private UtilisateurDto modificateur;
    private Long idTopic;
    private List<ReactionType> userReactions;

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

    /**
     * Getter
     *
     * @return userReactions
     */
    public List<ReactionType> getUserReactions() {
        return userReactions;
    }

    /**
     * Setter
     *
     * @param userReactions userReactions
     */
    public void setUserReactions(List<ReactionType> userReactions) {
        this.userReactions = userReactions;
    }
}
