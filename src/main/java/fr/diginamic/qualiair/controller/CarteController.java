package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.InfoCarteCommune;
import fr.diginamic.qualiair.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/map"})
public class CarteController {

    @Autowired
    private CommuneService communeService;

    @GetMapping("/commune/get-thumbnail-data")
    public ResponseEntity<List<InfoCarteCommune>> getThumbnailData(@RequestParam int nbHabitant) {
        return ResponseEntity.ok().body(communeService.getListCommunesDtoByPopulation(nbHabitant));
    }
}
