package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.service.RecensementParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller dédié à l'insertion de villes en base depuis des fichiers csv stockés avec le serveur
 */
@RestController
@RequestMapping({"/ville/insertion/recensement"})
public class VilleInsertionController {

    @Autowired
    private RecensementParserService recensementParserService;

    //TODO limit to SUPER-ADMIN

    /**
     * Charge en base les villes avec coordonées et habitants depuis deux jeux de fichiers
     *
     * @return message en cas de succès
     * @throws FileNotFoundException fichier non trouvé
     * @throws IOException           erreur de parsing
     */
    @PostMapping({"/load-from-server-hosted-files"})
    public String initializeLoadingFromLocalFiles() throws FileNotFoundException, IOException, ParsedDataException {
        recensementParserService.saveCommunesFromFichier();
        return "Insertion succesful";

    }
}
