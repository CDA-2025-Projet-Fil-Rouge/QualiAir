package fr.diginamic.qualiair.dto;

import fr.diginamic.qualiair.enumeration.AirPolluant;

public class AlerteInfo {
    private String communeNom;
    private String codeInsee;
    private String nomDepartement;
    private String nomRegion;
    private String nbHab;
    private AirPolluant polluant;
    private int valeurIndice;

    /**
     * Getter
     *
     * @return communeNom
     */
    public String getCommuneNom() {
        return communeNom;
    }

    /**
     * Setter
     *
     * @param communeNom sets value
     */
    public void setCommuneNom(String communeNom) {
        this.communeNom = communeNom;
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

    /**
     * Getter
     *
     * @return nbHab
     */
    public String getNbHab() {
        return nbHab;
    }

    /**
     * Setter
     *
     * @param nbHab sets value
     */
    public void setNbHab(String nbHab) {
        this.nbHab = nbHab;
    }

    /**
     * Getter
     *
     * @return polluant
     */
    public AirPolluant getPolluant() {
        return polluant;
    }

    /**
     * Setter
     *
     * @param polluant sets value
     */
    public void setPolluant(AirPolluant polluant) {
        this.polluant = polluant;
    }

    /**
     * Getter
     *
     * @return valeurIndice
     */
    public int getValeurIndice() {
        return valeurIndice;
    }

    /**
     * Setter
     *
     * @param valeurIndice sets value
     */
    public void setValeurIndice(int valeurIndice) {
        this.valeurIndice = valeurIndice;
    }
}
