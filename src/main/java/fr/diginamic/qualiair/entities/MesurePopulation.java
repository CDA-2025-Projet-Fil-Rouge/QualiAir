package fr.diginamic.qualiair.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesure_population")
public class MesurePopulation extends Mesure
{
    private int valeur;
    
    public MesurePopulation()
    {
    }
}
