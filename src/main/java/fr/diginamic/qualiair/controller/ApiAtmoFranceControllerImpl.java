package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.service.ApiAtmoFranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static fr.diginamic.qualiair.utils.DateUtils.getTimeStamp;

/**
 * Controller des requetes à destination de l'api Atmo-France
 * path "/external/api/atmo-france"
 */
@RestController
@RequestMapping({"/external/api/atmo-france"})
@Deprecated
public class ApiAtmoFranceControllerImpl implements ApiAtmoFranceController {

    @Autowired
    private ApiAtmoFranceService apiAtmoFranceService;

    @PostMapping("/air-quality/national-data/date/{date}")
    @Override

    public ResponseEntity<String> loadDailyFranceAirQualityData(@PathVariable String date) throws ExternalApiResponseException, UnnecessaryApiRequestException {
        LocalDateTime timeStamp = getTimeStamp();
        apiAtmoFranceService.saveDailyFranceAirQualityData(date, timeStamp);
        return ResponseEntity.ok().body("Données chargées en base avec succès");
    }

}
