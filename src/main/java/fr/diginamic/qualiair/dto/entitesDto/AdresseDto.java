package fr.diginamic.qualiair.dto.entitesDto;

public class AdresseDto {

    private String numeroRue;
    private String libelleRue;
    private String codePostal;
    private String nomCommune;

    public AdresseDto() {
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
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     * @param codePostal codePostal
     */
    public void setCodePostal(String codePostal) {
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
