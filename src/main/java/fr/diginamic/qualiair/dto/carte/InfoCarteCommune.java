package fr.diginamic.qualiair.dto.carte;

import fr.diginamic.qualiair.enumeration.DescriptionMeteo;

public class InfoCarteCommune {
    private Long id;
    private String codeInsee;
    private String nomVille;
    private int indiceQualiteAir;
    private DetailAir detailQualiteAir;
    private double latitude;
    private double longitude;
    private DescriptionMeteo descriptionMeteo;
    private DetailMeteo detailMeteo;

    public InfoCarteCommune(String nomVille, int indiceQualiteAir, double latitude, double longitude, DescriptionMeteo descriptionMeteo) {
        this.nomVille = nomVille;
        this.indiceQualiteAir = indiceQualiteAir;
        this.latitude = latitude;
        this.longitude = longitude;
        this.descriptionMeteo = descriptionMeteo;
    }

    public InfoCarteCommune() {
    }

    /**
     * Getter
     *
     * @return nomVille
     */
    public String getNomVille() {
        return nomVille;
    }

    /**
     * Setter
     *
     * @param nomVille sets value
     */
    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    /**
     * Getter
     *
     * @return indiceQualiteAir
     */
    public int getIndiceQualiteAir() {
        return indiceQualiteAir;
    }

    /**
     * Setter
     *
     * @param indiceQualiteAir sets value
     */
    public void setIndiceQualiteAir(int indiceQualiteAir) {
        this.indiceQualiteAir = indiceQualiteAir;
    }

    /**
     * Getter
     *
     * @return detailQualiteAir
     */
    public DetailAir getDetailQualiteAir() {
        return detailQualiteAir;
    }

    /**
     * Setter
     *
     * @param detailQualiteAir sets value
     */
    public void setDetailQualiteAir(DetailAir detailQualiteAir) {
        this.detailQualiteAir = detailQualiteAir;
    }

    /**
     * Getter
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Setter
     *
     * @param latitude sets value
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Setter
     *
     * @param longitude sets value
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter
     *
     * @return descriptionMeteo
     */
    public DescriptionMeteo getDescriptionMeteo() {
        return descriptionMeteo;
    }

    /**
     * Setter
     *
     * @param descriptionMeteo sets value
     */
    public void setDescriptionMeteo(DescriptionMeteo descriptionMeteo) {
        this.descriptionMeteo = descriptionMeteo;
    }

    /**
     * Getter
     *
     * @return detailMeteo
     */
    public DetailMeteo getDetailMeteo() {
        return detailMeteo;
    }

    /**
     * Setter
     *
     * @param detailMeteo sets value
     */
    public void setDetailMeteo(DetailMeteo detailMeteo) {
        this.detailMeteo = detailMeteo;
    }

    /**
     * Getter
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id sets value
     */
    public void setId(Long id) {
        this.id = id;
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
}
