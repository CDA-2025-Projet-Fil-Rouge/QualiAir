package fr.diginamic.qualiair.exception;

/**
 * Erreur de parsing de fichier csv
 */
public class ParsedDataException extends Exception {
    public ParsedDataException(String message) {
        super(message);
    }
}
