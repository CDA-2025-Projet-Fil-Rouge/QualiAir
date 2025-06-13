package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "coordonnee")
public class Coordonnee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Norme WGS84
     * y_wgs84
     */
    private double latitude;
    /**
     * Norme WGS84
     * x_wgs84
     */
    private double longitude;

    @OneToMany(mappedBy = "coordonnee")
    private Set<Commune> communes;

    @OneToMany(mappedBy = "coordonnee")
    private Set<Mesure> mesures;

    public Coordonnee() {
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
     * @return communes
     */
    public Set<Commune> getCommunes() {
        return communes;
    }

    /**
     * Setter
     *
     * @param communes sets value
     */
    public void setCommunes(Set<Commune> communes) {
        this.communes = communes;
    }

    /**
     * Getter
     *
     * @return mesures
     */
    public Set<Mesure> getMesures() {
        return mesures;
    }

    /**
     * Setter
     *
     * @param mesures sets value
     */
    public void setMesures(Set<Mesure> mesures) {
        this.mesures = mesures;
    }
}
