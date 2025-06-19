package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.AlerteInfo;
import fr.diginamic.qualiair.dto.DemandeNotification;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.RouteParamException;
import fr.diginamic.qualiair.service.AlerteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/alerte"})
public class AlerteController {

    @Autowired
    private AlerteService service;

    @GetMapping({"/get-all"})
    public ResponseEntity<Page<AlerteInfo>> getAllAlertes(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                                          @RequestParam(defaultValue = "4") int maxIndice, @RequestParam(defaultValue = "ATMO") AirPolluant polluant) {
        return ResponseEntity.ok().body(service.getAlertesByPolluant(polluant, page, size, maxIndice));
    }

    @PostMapping("/notify-users")
    public ResponseEntity<?> notifyUsers(@RequestBody DemandeNotification demandeNotification) throws RouteParamException, ExternalApiResponseException, ParsedDataException {
        service.sendAlert(demandeNotification);
        return ResponseEntity.ok().build();
    }

}
