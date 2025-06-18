package fr.diginamic.qualiair.dto.insertion;

public class DepartementDto {
    private String nomDepartement;
    private String codeDepartement;
    private String regionId;

    public DepartementDto() {
    }

    /**
     * Getter
     *
     * @return regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * Setter
     *
     * @param regionId sets value
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
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
}
