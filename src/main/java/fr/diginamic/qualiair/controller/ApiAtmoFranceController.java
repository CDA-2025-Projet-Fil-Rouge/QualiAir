package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Extraction depuis l'api Atmo France", description = "Gère la récupération des relevés qualité de l'air depuis l'Api Atmo-France")
@Deprecated
public interface ApiAtmoFranceController {
    /**
     * Load in base daily air quality data from Atmo France.
     * This endpoint is primarily intended for scheduled operations.
     * The API refreshes its data every day between 11am and 3pm.
     * Manual calls should be made with caution as they may:
     * - Impact system performance
     * - Trigger rate limits on the external API
     * Note: This operation will fail if data for the specified date already exists
     * to prevent accidental data override.
     *
     * @param date target date in YYYY-MM-DD format
     * @return success message
     * @throws ExternalApiResponseException   if the API call fails
     * @throws UnnecessaryApiRequestException if data for the date already exists
     */
    @PostMapping("/air-quality/national-data/date/{date}")
    ResponseEntity<String> loadDailyFranceAirQualityData(@PathVariable String date) throws ExternalApiResponseException, UnnecessaryApiRequestException;
}
