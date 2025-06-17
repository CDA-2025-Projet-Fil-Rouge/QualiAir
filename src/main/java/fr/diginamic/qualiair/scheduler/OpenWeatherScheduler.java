package fr.diginamic.qualiair.scheduler;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.service.ApiOpenWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OpenWeather scheduler tasks
 */
@Service
public class OpenWeatherScheduler {
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherScheduler.class);

    /**
     * Les villes sont filtrées par nombre d'habitants afin de limiter le nombre de requetes, afin de ne pas saturer notre clef api OpenWeather
     */
    @Value("${ow.schedule.request.limit}")
    private int HAB;
    @Autowired
    private ApiOpenWeatherService service;

    /**
     * Charge en base la meteo  pour chaque ville (de plus de X habitants) à chaque heure, 1 minute et 10 secondes entre 06h et 22h
     */
    @Scheduled(cron = "${ow.schedule.cron.meteo}")
    public void fetchLocalWeatherForTopCitiesEveryHour() {
        List<Commune> communes = service.getCommunesByNbHab(HAB);
        logger.info("Found {} communes with >= {} habitants", communes.size(), HAB);
        logger.info("Scheduled task running at {}", LocalDateTime.now());
        for (Commune commune : communes) {
            try {
                service.requestAndSaveCurrentForecast(commune);
            } catch (UnnecessaryApiRequestException | FunctionnalException | ExternalApiResponseException |
                     ParsedDataException e) {
                logger.debug("Failed to get weather data for {}, with error : {}", commune.getNomSimple(), e.getMessage());
            }
        }
    }
}
