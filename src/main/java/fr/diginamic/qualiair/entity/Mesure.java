package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "mesure")

public class Mesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesure")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeMesure typeMesure;
    @Column(name = "date_enregistrement")
    private LocalDateTime dateEnregistrement;
    @Column(name = "date_releve")
    private LocalDateTime dateReleve;
    @ManyToOne
    @JoinColumn(name = "id_coordonnee")
    private Coordonnee coordonnee;

    @OneToMany(mappedBy = "mesure")
    private Set<MesureAir> mesuresAir;
    @OneToMany(mappedBy = "mesure")
    private Set<MesurePrevision> mesuresPrev;
    @OneToMany(mappedBy = "mesure")
    private Set<MesurePopulation> mesuresPop;

    public Mesure() {
    }

    public Mesure(TypeMesure typeMesure, LocalDateTime dateEnregistrement, LocalDateTime dateReleve, Coordonnee coordonnee) {
        this.typeMesure = typeMesure;
        this.dateEnregistrement = dateEnregistrement;
        this.dateReleve = dateReleve;
        this.coordonnee = coordonnee;
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

    /**
     * Getter
     *
     * @return mesuresAir
     */
    public Set<MesureAir> getMesuresAir() {
        return mesuresAir;
    }

    /**
     * Getter
     *
     * @return mesuresPrev
     */
    public Set<MesurePrevision> getMesuresPrev() {
        return mesuresPrev;
    }

    /**
     * Getter
     *
     * @return mesuresPop
     */
    public Set<MesurePopulation> getMesuresPop() {
        return mesuresPop;
    }

    /**
     * Getter
     *
     * @return dateReleve
     */
    public LocalDateTime getDateReleve() {
        return dateReleve;
    }

    /**
     * Setter
     *
     * @param dateReleve sets value
     */
    public void setDateReleve(LocalDateTime dateReleve) {
        this.dateReleve = dateReleve;
    }
}
