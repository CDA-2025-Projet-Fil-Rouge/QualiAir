package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mesure")
public class Mesure
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    @Column(name = "date_releve")
    private LocalDateTime date;
    @Column(name = "date_enregistrement")
    private LocalDateTime dateEnregistrement;
    
    @ManyToOne
    @JoinColumn(name = "id_coordonnee")
    private Coordonnee coordonnee;
    
    public Mesure()
    {
    }
    
    /**
     * Getter
     * @return id
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Getter
     * @return nom
     */
    public String getNom()
    {
        return nom;
    }
    
    /**
     * Setter
     * @param nom sets value
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    
    /**
     * Getter
     * @return date
     */
    public LocalDateTime getDate()
    {
        return date;
    }
    
    /**
     * Setter
     * @param date sets value
     */
    public void setDate(LocalDateTime date)
    {
        this.date = date;
    }
    
    /**
     * Getter
     * @return dateEnregistrement
     */
    public LocalDateTime getDateEnregistrement()
    {
        return dateEnregistrement;
    }
    
    /**
     * Setter
     * @param dateEnregistrement sets value
     */
    public void setDateEnregistrement(LocalDateTime dateEnregistrement)
    {
        this.dateEnregistrement = dateEnregistrement;
    }
    
    /**
     * Getter
     * @return coordonnee
     */
    public Coordonnee getCoordonnee()
    {
        return coordonnee;
    }
    
    /**
     * Setter
     * @param coordonnee sets value
     */
    public void setCoordonnee(Coordonnee coordonnee)
    {
        this.coordonnee = coordonnee;
    }
}
