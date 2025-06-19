package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.exception.FileNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface RecensementParserService {
    /**
     * Lance le processus global de lecture, parsing et insertion des fichiers de recensement.
     * Ce processus est transactionnel : tout échec bloque l'ensemble de l'opération.
     *
     * @throws IOException           en cas d'erreur de lecture d'un fichier
     * @throws FileNotFoundException si un chemin de fichier requis est manquant
     */
    @Transactional
    void saveCommunesFromFichier() throws IOException, FileNotFoundException;
}
