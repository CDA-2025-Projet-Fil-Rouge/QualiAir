package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/map"})
public class CarteControllerImpl implements CarteController {

    @Autowired
    private CommuneService communeService;

    @CrossOrigin
    @GetMapping("/commune/get-all-thumbnail-data")
    @Override
    public ResponseEntity<List<InfoCarteCommune>> getThumbnailData(@RequestParam int nbHabitant) {
        return ResponseEntity.ok().body(communeService.getListCommunesDtoByPopulation(nbHabitant));
    }

    @Override
    @CrossOrigin
    @GetMapping("/commune/get-thumbnail-data/{codeInsee}")
    public ResponseEntity<InfoCarteCommune> getThumbnailDataByCodeInsee(@PathVariable String codeInsee) {
        return ResponseEntity.ok().body(communeService.getCommuneDtoByCodeInsee(codeInsee));
    }
}
