package fr.diginamic.qualiair.dto;

public class InfoCarteCommuneDetailQualiteAir {
    private String ozone;
    private String dioxydeAzote;
    private String particules10;
    private String particules25;
    private String dioxydeSoufre;

    public InfoCarteCommuneDetailQualiteAir() {
    }

    public InfoCarteCommuneDetailQualiteAir(String ozone, String dioxydeAzote, String particules10, String particules25, String dioxydeSoufre) {
        this.ozone = ozone;
        this.dioxydeAzote = dioxydeAzote;
        this.particules10 = particules10;
        this.particules25 = particules25;
        this.dioxydeSoufre = dioxydeSoufre;
    }

    /**
     * Getter
     *
     * @return ozone
     */
    public String getOzone() {
        return ozone;
    }

    /**
     * Setter
     *
     * @param ozone sets value
     */
    public void setOzone(String ozone) {
        this.ozone = ozone;
    }

    /**
     * Getter
     *
     * @return dioxydeAzote
     */
    public String getDioxydeAzote() {
        return dioxydeAzote;
    }

    /**
     * Setter
     *
     * @param dioxydeAzote sets value
     */
    public void setDioxydeAzote(String dioxydeAzote) {
        this.dioxydeAzote = dioxydeAzote;
    }

    /**
     * Getter
     *
     * @return particules10
     */
    public String getParticules10() {
        return particules10;
    }

    /**
     * Setter
     *
     * @param particules10 sets value
     */
    public void setParticules10(String particules10) {
        this.particules10 = particules10;
    }

    /**
     * Getter
     *
     * @return particules25
     */
    public String getParticules25() {
        return particules25;
    }

    /**
     * Setter
     *
     * @param particules25 sets value
     */
    public void setParticules25(String particules25) {
        this.particules25 = particules25;
    }

    /**
     * Getter
     *
     * @return dioxydeSoufre
     */
    public String getDioxydeSoufre() {
        return dioxydeSoufre;
    }

    /**
     * Setter
     *
     * @param dioxydeSoufre sets value
     */
    public void setDioxydeSoufre(String dioxydeSoufre) {
        this.dioxydeSoufre = dioxydeSoufre;
    }
}
