package fr.diginamic.qualiair.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesure_air")
public class MesureAir extends Mesure
{
    private int indice;
    private int valeur;
    private int unite;
    
    public MesureAir()
    {
    }
}
