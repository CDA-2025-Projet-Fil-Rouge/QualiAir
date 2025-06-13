package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.service.RecensementParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping({"/ville/insertion/recensement"})
public class VilleInsertionController {

    @Autowired
    private RecensementParserService recensementParserService;

    //TODO limit to SUPER-ADMIN
    @PostMapping({"/load-from-server-hosted-files"})
    public String initializeLoadingFromLocalFiles() throws FileNotFoundException, IOException {
        recensementParserService.saveCommunesFromFichier();
        return "Insertion succesful";

    }
}
