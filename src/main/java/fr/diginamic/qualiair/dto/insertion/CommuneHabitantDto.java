package fr.diginamic.qualiair.dto.insertion;

/**
 * DTO containing useful elements from the file communeWithHab
 */
public class CommuneHabitantDto implements DtoFromCsv {

    private String codeInsee;
    private String populationMunicipale;


    public CommuneHabitantDto() {
    }

    /**
     * Getter
     *
     * @return codeInsee
     */
    public String getCodeInsee() {
        return codeInsee;
    }

    /**
     * Setter
     *
     * @param codeInsee sets value
     */
    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
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
