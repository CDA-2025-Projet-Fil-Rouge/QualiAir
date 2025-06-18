package fr.diginamic.qualiair.dto.entitesDto;

public class AdresseDto {

    private Long id;
    private String numeroRue;
    private String libelleRue;
    private int codePostal;
    private String nomCommune;

    public AdresseDto() {
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
     * @return numeroRue
     */
    public String getNumeroRue() {
        return numeroRue;
    }

    /**
     * Setter
     * @param numeroRue numeroRue
     */
    public void setNumeroRue(String numeroRue) {
        this.numeroRue = numeroRue;
    }

    /**
     * Getter
     * @return libelleRue
     */
    public String getLibelleRue() {
        return libelleRue;
    }

    /**
     * Setter
     * @param libelleRue libelleRue
     */
    public void setLibelleRue(String libelleRue) {
        this.libelleRue = libelleRue;
    }

    /**
     * Getter
     * @return codePostal
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     * @param codePostal codePostal
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Getter
     * @return nomCommune
     */
    public String getNomCommune() {
        return nomCommune;
    }

    /**
     * Setter
     * @param nomCommune nomCommune
     */
    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }
}
