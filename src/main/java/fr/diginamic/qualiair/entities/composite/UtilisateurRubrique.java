package fr.diginamic.qualiair.entities.composite;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Clef composite pour Rubrique Modification
 */
@Embeddable
public class UtilisateurRubrique
{
    private Long utilisateurId;
    private Long rubriqueId;
    
    public UtilisateurRubrique()
    {
    }
    
    public UtilisateurRubrique(Long rubriqueId, Long utilisateurId)
    {
        this.rubriqueId = rubriqueId;
        this.utilisateurId = utilisateurId;
    }
    
    /**
     * Getter
     * @return rubriqueId
     */
    public Long getRubriqueId()
    {
        return rubriqueId;
    }
    
    /**
     * Setter
     * @param rubriqueId sets value
     */
    public void setRubriqueId(Long rubriqueId)
    {
        this.rubriqueId = rubriqueId;
    }
    
    /**
     * Getter
     * @return utilisateurId
     */
    public Long getUtilisateurId()
    {
        return utilisateurId;
    }
    
    /**
     * Setter
     * @param utilisateurId sets value
     */
    public void setUtilisateurId(Long utilisateurId)
    {
        this.utilisateurId = utilisateurId;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        UtilisateurRubrique that = (UtilisateurRubrique) o;
        return Objects.equals(utilisateurId, that.utilisateurId) && Objects.equals(rubriqueId,
                                                                                   that.rubriqueId);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(utilisateurId, rubriqueId);
    }
}
