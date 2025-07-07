package fr.diginamic.qualiair.dto.insertion;

public class RegionDto {
    private String codeRegion;
    private String nomRegion;
    private String id;

    public RegionDto() {
    }

    /**
     * Getter
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id sets value
     */
    public void setId(String id) {
        this.id = id;
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
