package fr.diginamic.qualiair.entity;

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
    
    /**
     * Getter
     * @return id
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Getter
     * @return nom
     */
    public String getNom()
    {
        return nom;
    }
    
    /**
     * Setter
     * @param nom sets value
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    
    /**
     * Getter
     * @return code
     */
    public int getCode()
    {
        return code;
    }
    
    /**
     * Setter
     * @param code sets value
     */
    public void setCode(int code)
    {
        this.code = code;
    }
    
    /**
     * Getter
     * @return departements
     */
    public Set<Departement> getDepartements()
    {
        return departements;
    }
    
    /**
     * Setter
     * @param departements sets value
     */
    public void setDepartements(Set<Departement> departements)
    {
        this.departements = departements;
    }
}
