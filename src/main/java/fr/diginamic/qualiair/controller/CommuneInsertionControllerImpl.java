package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.service.RecensementParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Controller dédié à l'insertion de villes en base depuis des fichiers csv stockés avec le serveur
 */
@RestController
@RequestMapping({"/commune/recensement"})
public class CommuneInsertionControllerImpl implements CommuneInsertionController {

    @Autowired
    private RecensementParserService recensementParserService;

    @CrossOrigin
    @PostMapping({"/insertion/load-from-server-hosted-files"})
    @Override
    public String initializeLoadingFromLocalFiles() throws FileNotFoundException, IOException {
        recensementParserService.saveCommunesFromFichier();
        return "Insertion succesful";
    }


}
