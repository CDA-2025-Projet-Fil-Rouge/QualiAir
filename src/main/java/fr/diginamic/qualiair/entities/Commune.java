package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "commune")
public class Commune
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    @Column(name = "code_postal")
    private int codePostal;
    @Column(name = "code_insee")
    private int codeInsee;
    
    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;
    
    @ManyToOne
    @JoinColumn(name = "id_coordonnee")
    private Coordonnee coordonnee;
    
    @OneToMany(mappedBy = "commune")
    private Set<Adresse> adresse;
    
    public Commune()
    {
    }
}
