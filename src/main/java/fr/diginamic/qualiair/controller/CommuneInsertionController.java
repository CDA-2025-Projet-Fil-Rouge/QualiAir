package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.FileNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

public interface CommuneInsertionController {
    /**
     * Charge en base les villes avec coordonées et habitants depuis deux jeux de fichiers
     *
     * @return message en cas de succès
     * @throws FileNotFoundException fichier non trouvé
     * @throws IOException           erreur de parsing
     */
    @PostMapping({"/insertion/load-from-server-hosted-files"})
    String initializeLoadingFromLocalFiles() throws FileNotFoundException, IOException;
}
