package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mesure_prevision")
public class MesurePrevision extends Mesure
{
    private String nature;
    private double valeur;
    private String unite;
    
    @Enumerated(EnumType.STRING)
    private TypeReleve typeReleve;
    
    public MesurePrevision()
    {
    }
}
