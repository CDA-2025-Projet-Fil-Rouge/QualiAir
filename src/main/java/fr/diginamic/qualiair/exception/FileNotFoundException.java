package fr.diginamic.qualiair.exception;

/**
 * Erreur si le fichier n'est pas trouvé coté
 */
public class FileNotFoundException extends Exception {
    public FileNotFoundException(String message) {
        super(message);
    }
}
