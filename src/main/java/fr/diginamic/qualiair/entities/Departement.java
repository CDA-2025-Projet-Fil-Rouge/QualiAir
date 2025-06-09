package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "departement")
public class Departement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String code;
    
    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region region;
    
    @OneToMany(mappedBy = "departement")
    private Set<Commune> commune;
    
    public Departement()
    {
    }
}
