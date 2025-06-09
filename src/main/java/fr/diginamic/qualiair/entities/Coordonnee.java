package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "coordonnee")
public class Coordonnee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double latitude;
    private double longitude;
    
    @OneToMany(mappedBy = "coordonnee")
    private Set<Commune> communes;
    
    @OneToMany(mappedBy = "coordonnee")
    private Set<Mesure> mesures;
    
    public Coordonnee()
    {
    }
}
