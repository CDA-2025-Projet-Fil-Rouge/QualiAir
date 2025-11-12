package fr.diginamic.qualiair.dto.notification;

import fr.diginamic.qualiair.enumeration.TypeAlerte;

public class DemandeNotification {
    private TypeAlerte type;
    private String code;
    private String message;

    /**
     * Getter
     *
     * @return type
     */
    public TypeAlerte getType() {
        return type;
    }

    /**
     * Getter
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Sets type for the class DemandeNotification.
     *
     * @param type value of type
     */
    public void setType(TypeAlerte type)
    {
        this.type = type;
    }
    
    /**
     * Sets code for the class DemandeNotification.
     *
     * @param code value of code
     */
    public void setCode(String code)
    {
        this.code = code;
    }
    
    /**
     * Sets message for the class DemandeNotification.
     *
     * @param message value of message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}