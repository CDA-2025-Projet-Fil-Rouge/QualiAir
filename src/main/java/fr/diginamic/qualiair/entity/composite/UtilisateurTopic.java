package fr.diginamic.qualiair.entity.composite;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Clef composite pour Topic Modification
 */
@Embeddable
public class UtilisateurTopic
{
    private Long utilisateurId;
    private Long topicId;
    
    public UtilisateurTopic()
    {
    }
    
    public UtilisateurTopic(Long topicId, Long utilisateurId)
    {
        this.topicId = topicId;
        this.utilisateurId = utilisateurId;
    }
    
    /**
     * Getter
     * @return TopicId
     */
    public Long getTopicId()
    {
        return topicId;
    }
    
    /**
     * Setter
     * @param topicId sets value
     */
    public void setTopicId(Long topicId)
    {
        this.topicId = topicId;
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
        UtilisateurTopic that = (UtilisateurTopic) o;
        return Objects.equals(utilisateurId, that.utilisateurId) && Objects.equals(topicId,
                                                                                   that.topicId);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(utilisateurId, topicId);
    }
}
