package fr.diginamic.qualiair.dto.entitesDto;


import fr.diginamic.qualiair.entity.RoleUtilisateur;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UtilisateurDto {

    private Long id;

    private String prenom;
    private String nom;
    LocalDate dateNaissance;
    private LocalDateTime dateInscription;
    private String email;
    private String motDePasse;
    private RoleUtilisateur role;
    private Long idAdresse;

    public UtilisateurDto() {
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
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter
     * @return prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter
     * @param prenom prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
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
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter
     * @return dateNaissance
     */
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Setter
     * @param dateNaissance dateNaissance
     */
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Getter
     * @return dateInscription
     */
    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    /**
     * Setter
     * @param dateInscription dateInscription
     */
    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    /**
     * Getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter
     * @return motDePasse
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Setter
     * @param motDePasse motDePasse
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * Getter
     * @return role
     */
    public RoleUtilisateur getRole() {
        return role;
    }

    /**
     * Setter
     * @param role role
     */
    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }

    /**
     * Getter
     * @return idAdresse
     */
    public Long getIdAdresse() {
        return idAdresse;
    }

    /**
     * Setter
     * @param idAdresse idAdresse
     */
    public void setIdAdresse(Long idAdresse) {
        this.idAdresse = idAdresse;
    }
}
