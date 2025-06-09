package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "adresse")
public class Adresse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_rue")
    private String numeroRue;
    @Column(name = "libelle_rue")
    private String libelleRue;
    
    @ManyToOne
    @JoinColumn(name = "id_commune")
    private Commune commune;
    
    @OneToMany(mappedBy = "adresse")
    private Set<Utilisateur> utilisateurs;
    
    public Adresse()
    {
    }
}
