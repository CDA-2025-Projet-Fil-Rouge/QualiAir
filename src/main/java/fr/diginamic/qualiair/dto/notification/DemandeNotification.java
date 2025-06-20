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
}
