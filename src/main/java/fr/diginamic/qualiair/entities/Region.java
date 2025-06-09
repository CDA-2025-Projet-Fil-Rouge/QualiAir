package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "region")
public class Region
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private int code;
    
    @OneToMany(mappedBy = "region")
    private Set<Departement> departements;
    
    public Region()
    {
    }
}
