package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.AlerteInfo;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.ListingAlerte;
import fr.diginamic.qualiair.service.AlerteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/alerte"})
public class AlerteController {

    @Autowired
    private AlerteService service;

    @GetMapping({"/get-all"})
    public List<AlerteInfo> getAllAlertes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "ATMO") AirPolluant polluant) {
        //todo
    }

    @PostMapping("/notify-users/")
    public ResponseEntity<?> post(@RequestBody ListingAlerte listing) {
        //todo
    }

}
