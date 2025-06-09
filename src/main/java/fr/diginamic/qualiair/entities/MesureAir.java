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
    
    /**
     * Getter
     * @return indice
     */
    public int getIndice()
    {
        return indice;
    }
    
    /**
     * Setter
     * @param indice sets value
     */
    public void setIndice(int indice)
    {
        this.indice = indice;
    }
    
    /**
     * Getter
     * @return valeur
     */
    public int getValeur()
    {
        return valeur;
    }
    
    /**
     * Setter
     * @param valeur sets value
     */
    public void setValeur(int valeur)
    {
        this.valeur = valeur;
    }
    
    /**
     * Getter
     * @return unite
     */
    public int getUnite()
    {
        return unite;
    }
    
    /**
     * Setter
     * @param unite sets value
     */
    public void setUnite(int unite)
    {
        this.unite = unite;
    }
}
