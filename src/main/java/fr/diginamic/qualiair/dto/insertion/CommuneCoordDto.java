package fr.diginamic.qualiair.dto.insertion;

/**
 * DTO containing useful elements from the file communeWithCoordinates
 */
public class CommuneCoordDto implements DtoFromCsv {
    private String codeCommuneINSEE;
    private String nomCommunePostal;
    private String codePostal;
    private String latitude;
    private String longitude;

    private String nomCommuneComplet;
    private String codeDepartement;
    private String nomDepartement;
    private String codeRegion;
    private String nomRegion;

    public CommuneCoordDto() {
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
     * @return nomCommunePostal
     */
    public String getNomCommunePostal() {
        return nomCommunePostal;
    }

    /**
     * Setter
     *
     * @param nomCommunePostal sets value
     */
    public void setNomCommunePostal(String nomCommunePostal) {
        this.nomCommunePostal = nomCommunePostal;
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


    /**
     * Getter
     *
     * @return nomCommuneComplet
     */
    public String getNomCommuneComplet() {
        return nomCommuneComplet;
    }

    /**
     * Setter
     *
     * @param nomCommuneComplet sets value
     */
    public void setNomCommuneComplet(String nomCommuneComplet) {
        this.nomCommuneComplet = nomCommuneComplet;
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
     * @return nomDepartement
     */
    public String getNomDepartement() {
        return nomDepartement;
    }

    /**
     * Setter
     *
     * @param nomDepartement sets value
     */
    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }

    /**
     * Getter
     *
     * @return codeRegion
     */
    public String getCodeRegion() {
        return codeRegion;
    }

    /**
     * Setter
     *
     * @param codeRegion sets value
     */
    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }

    /**
     * Getter
     *
     * @return nomRegion
     */
    public String getNomRegion() {
        return nomRegion;
    }

    /**
     * Setter
     *
     * @param nomRegion sets value
     */
    public void setNomRegion(String nomRegion) {
        this.nomRegion = nomRegion;
    }
}
