package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CarteController {
    @GetMapping("/commune/get-thumbnail-data")
    ResponseEntity<List<InfoCarteCommune>> getThumbnailData(@RequestParam int nbHabitant);
}
