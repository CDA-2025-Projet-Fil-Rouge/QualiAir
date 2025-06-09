package fr.diginamic.qualiair.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesure_prevision_probabilite")
public class MesurePrevisionProbabilite extends MesurePrevision
{
    private double probabilite;
    
    public MesurePrevisionProbabilite()
    {
    }
}
