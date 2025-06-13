package fr.diginamic.qualiair.dto.insertion;

/**
 * DTO containing useful elements from the file communeWithHab
 */
public class CommuneHabitantDto implements DtoFromCsv {
    //    private String codeRegion;
//    private String nomRegion;
//    private String codeDepartment;
    private String nomCommune;
    private String populationMunicipale;
    private String populationTotale;

    public CommuneHabitantDto() {
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
     * @return populationTotale
     */
    public String getPopulationTotale() {
        return populationTotale;
    }

    /**
     * Setter
     *
     * @param populationTotale sets value
     */
    public void setPopulationTotale(String populationTotale) {
        this.populationTotale = populationTotale;
    }

    /**
     * Getter
     *
     * @return populationMunicipale
     */
    public String getPopulationMunicipale() {
        return populationMunicipale;
    }

    /**
     * Setter
     *
     * @param populationMunicipale sets value
     */
    public void setPopulationMunicipale(String populationMunicipale) {
        this.populationMunicipale = populationMunicipale;
    }
}
