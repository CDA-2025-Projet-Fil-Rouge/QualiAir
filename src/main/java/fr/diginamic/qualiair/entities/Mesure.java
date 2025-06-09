package fr.diginamic.qualiair.entities;

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
}
