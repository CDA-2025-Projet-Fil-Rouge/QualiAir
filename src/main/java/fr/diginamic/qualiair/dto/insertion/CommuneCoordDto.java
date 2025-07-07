package fr.diginamic.qualiair.dto.insertion;

/**
 * DTO containing useful elements from the file communeWithCoordinates
 */
public class CommuneCoordDto implements DtoFromCsv {
    private String codeDepartement;
    private String nomCommune;
    private String nomCommuneSimple;
    private String nomCommuneReel;
    private String codePostal;
    private String codeCommuneINSEE;
    private String latitude;
    private String longitude;

    public CommuneCoordDto() {
    }


    /**
     * Getter
     *
     * @return codeDepartement
     */
    public String getCodeDepartement() {
        return codeDepartement;
    }

    /**
     * Setter
     *
     * @param codeDepartement sets value
     */
    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    /**
     * Getter
     *
     * @return nomCommune
     */
    public String getNomCommune() {
        return nomCommune;
    }

    /**
     * Setter
     *
     * @param nomCommune sets value
     */
    public void setNomCommune(String nomCommune) {
        this.nomCommune = nomCommune;
    }

    /**
     * Getter
     *
     * @return nomCommuneSimple
     */
    public String getNomCommuneSimple() {
        return nomCommuneSimple;
    }

    /**
     * Setter
     *
     * @param nomCommuneSimple sets value
     */
    public void setNomCommuneSimple(String nomCommuneSimple) {
        this.nomCommuneSimple = nomCommuneSimple;
    }

    /**
     * Getter
     *
     * @return nomCommuneReel
     */
    public String getNomCommuneReel() {
        return nomCommuneReel;
    }

    /**
     * Setter
     *
     * @param nomCommuneReel sets value
     */
    public void setNomCommuneReel(String nomCommuneReel) {
        this.nomCommuneReel = nomCommuneReel;
    }

    /**
     * Getter
     *
     * @return codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     *
     * @param codePostal sets value
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Getter
     *
     * @return codeCommuneINSEE
     */
    public String getCodeCommuneINSEE() {
        return codeCommuneINSEE;
    }

    /**
     * Setter
     *
     * @param codeCommuneINSEE sets value
     */
    public void setCodeCommuneINSEE(String codeCommuneINSEE) {
        this.codeCommuneINSEE = codeCommuneINSEE;
    }

    /**
     * Getter
     *
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter
     *
     * @param latitude sets value
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter
     *
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter
     *
     * @param longitude sets value
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
