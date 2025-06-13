package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.service.ApiAtmoFranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/external/api/atmo-france"})
public class ApiAtmoFranceController {

    @Autowired
    private ApiAtmoFranceService apiAtmoFranceService;

    /**
     * Load in base daily air quality data from Atmo France
     * The api refreshes its data every day between 11am and 3pm
     *
     * @param date target date
     * @return ?
     */
    @PostMapping("/air-quality/national-data/date/{date}")
    public ResponseEntity<String> loadDailyFranceAirQualityData(@PathVariable String date) throws ExternalApiResponseException, FileNotFoundException {
        apiAtmoFranceService.loadDailyFranceAirQualityData(date);
        return ResponseEntity.ok().body("Données chargées en base avec succès");
    }

}
