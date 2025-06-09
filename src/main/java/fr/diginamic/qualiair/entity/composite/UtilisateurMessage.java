package fr.diginamic.qualiair.entity.composite;

import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Clef composite pour Message Modification
 */
@Embeddable
public class UtilisateurMessage
{
    private Long utilisateurId;
    private Long messageId;
    
    public UtilisateurMessage()
    {
    }
    
    public UtilisateurMessage(Long messageId, Long utilisateurId)
    {
        this.messageId = messageId;
        this.utilisateurId = utilisateurId;
    }
    
    /**
     * Getter
     * @return messageId
     */
    public Long getMessageId()
    {
        return messageId;
    }
    
    /**
     * Setter
     * @param messageId sets value
     */
    public void setMessageId(Long messageId)
    {
        this.messageId = messageId;
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
        UtilisateurMessage that = (UtilisateurMessage) o;
        return Objects.equals(utilisateurId, that.utilisateurId) && Objects.equals(messageId,
                                                                                   that.messageId);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(utilisateurId, messageId);
    }
}
