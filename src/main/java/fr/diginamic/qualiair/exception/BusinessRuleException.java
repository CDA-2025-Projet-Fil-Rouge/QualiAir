package fr.diginamic.qualiair.exception;

/**
 * Erreur de validation de règles metier sur une entité JPA
 */
public class BusinessRuleException extends Exception {
    public BusinessRuleException(String message) {
        super(message);
    }
}
