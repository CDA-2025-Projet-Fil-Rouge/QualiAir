package fr.diginamic.qualiair.dto;

public class InfoCarteCommuneDetailMeteo {
    private String temperature;
    private String temperatureRessentie;
    private String temperatureMin;
    private String temperatureMax;
    private String couvertureNuageuse;
    private String pluie;
    private String neige;
    private String ventVitesse;
    private String ventRafale;
    private String ventOrientation;
    private String visibilite;

    public InfoCarteCommuneDetailMeteo(String temperature, String temperatureRessentie, String temperatureMin, String temperatureMax, String couvertureNuageuse, String pluie, String neige, String ventVitesse, String ventRafale, String ventOrientation, String visibilite) {
        this.temperature = temperature;
        this.temperatureRessentie = temperatureRessentie;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.couvertureNuageuse = couvertureNuageuse;
        this.pluie = pluie;
        this.neige = neige;
        this.ventVitesse = ventVitesse;
        this.ventRafale = ventRafale;
        this.ventOrientation = ventOrientation;
        this.visibilite = visibilite;
    }

    public InfoCarteCommuneDetailMeteo() {
    }

    /**
     * Getter
     *
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Setter
     *
     * @param temperature sets value
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * Getter
     *
     * @return temperatureRessentie
     */
    public String getTemperatureRessentie() {
        return temperatureRessentie;
    }

    /**
     * Setter
     *
     * @param temperatureRessentie sets value
     */
    public void setTemperatureRessentie(String temperatureRessentie) {
        this.temperatureRessentie = temperatureRessentie;
    }

    /**
     * Getter
     *
     * @return temperatureMin
     */
    public String getTemperatureMin() {
        return temperatureMin;
    }

    /**
     * Setter
     *
     * @param temperatureMin sets value
     */
    public void setTemperatureMin(String temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    /**
     * Getter
     *
     * @return temperatureMax
     */
    public String getTemperatureMax() {
        return temperatureMax;
    }

    /**
     * Setter
     *
     * @param temperatureMax sets value
     */
    public void setTemperatureMax(String temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    /**
     * Getter
     *
     * @return couvertureNuageuse
     */
    public String getCouvertureNuageuse() {
        return couvertureNuageuse;
    }

    /**
     * Setter
     *
     * @param couvertureNuageuse sets value
     */
    public void setCouvertureNuageuse(String couvertureNuageuse) {
        this.couvertureNuageuse = couvertureNuageuse;
    }

    /**
     * Getter
     *
     * @return pluie
     */
    public String getPluie() {
        return pluie;
    }

    /**
     * Setter
     *
     * @param pluie sets value
     */
    public void setPluie(String pluie) {
        this.pluie = pluie;
    }

    /**
     * Getter
     *
     * @return neige
     */
    public String getNeige() {
        return neige;
    }

    /**
     * Setter
     *
     * @param neige sets value
     */
    public void setNeige(String neige) {
        this.neige = neige;
    }

    /**
     * Getter
     *
     * @return ventVitesse
     */
    public String getVentVitesse() {
        return ventVitesse;
    }

    /**
     * Setter
     *
     * @param ventVitesse sets value
     */
    public void setVentVitesse(String ventVitesse) {
        this.ventVitesse = ventVitesse;
    }

    /**
     * Getter
     *
     * @return ventRafale
     */
    public String getVentRafale() {
        return ventRafale;
    }

    /**
     * Setter
     *
     * @param ventRafale sets value
     */
    public void setVentRafale(String ventRafale) {
        this.ventRafale = ventRafale;
    }

    /**
     * Getter
     *
     * @return ventOrientation
     */
    public String getVentOrientation() {
        return ventOrientation;
    }

    /**
     * Setter
     *
     * @param ventOrientation sets value
     */
    public void setVentOrientation(String ventOrientation) {
        this.ventOrientation = ventOrientation;
    }

    /**
     * Getter
     *
     * @return visibilite
     */
    public String getVisibilite() {
        return visibilite;
    }

    /**
     * Setter
     *
     * @param visibilite sets value
     */
    public void setVisibilite(String visibilite) {
        this.visibilite = visibilite;
    }
}
