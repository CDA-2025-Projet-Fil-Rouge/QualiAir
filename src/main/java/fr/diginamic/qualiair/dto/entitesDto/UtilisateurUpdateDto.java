package fr.diginamic.qualiair.dto.entitesDto;

public class UtilisateurUpdateDto {

    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String ancienMotDePasse;
    private String nouveauMotDePasse;


    public UtilisateurUpdateDto() {
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
     * @return ancienMotDePasse
     */
    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    /**
     * Setter
     * @param ancienMotDePasse ancienMotDePasse
     */
    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    /**
     * Getter
     * @return nouveauMotDePasse
     */
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    /**
     * Setter
     * @param nouveauMotDePasse nouveauMotDePasse
     */
    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
}
