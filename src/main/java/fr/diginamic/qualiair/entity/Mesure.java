package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mesure")
public class Mesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesure")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeMesure typeMesure;
    @Column(name = "date_releve")
    private LocalDateTime dateReleve;
    @Column(name = "date_enregistrement")
    private LocalDateTime dateEnregistrement;

    @ManyToOne
    @JoinColumn(name = "id_coordonnee")
    private Coordonnee coordonnee;

    public Mesure() {
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
     * @return typeMesure
     */
    public TypeMesure getTypeMesure() {
        return typeMesure;
    }

    /**
     * Setter
     *
     * @param typeMesure sets value
     */
    public void setTypeMesure(TypeMesure typeMesure) {
        this.typeMesure = typeMesure;
    }

    /**
     * Getter
     *
     * @return date
     */
    public LocalDateTime getDateReleve() {
        return dateReleve;
    }

    /**
     * Setter
     *
     * @param date sets value
     */
    public void setDateReleve(LocalDateTime date) {
        this.dateReleve = date;
    }

    /**
     * Getter
     *
     * @return dateEnregistrement
     */
    public LocalDateTime getDateEnregistrement() {
        return dateEnregistrement;
    }

    /**
     * Setter
     *
     * @param dateEnregistrement sets value
     */
    public void setDateEnregistrement(LocalDateTime dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    /**
     * Getter
     *
     * @return coordonnee
     */
    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    /**
     * Setter
     *
     * @param coordonnee sets value
     */
    public void setCoordonnee(Coordonnee coordonnee) {
        this.coordonnee = coordonnee;
    }
}
