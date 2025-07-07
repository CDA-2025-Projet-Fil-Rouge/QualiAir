package fr.diginamic.qualiair.scheduler;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.FunctionnalException;
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

import static fr.diginamic.qualiair.utils.DateUtils.getTimeStamp;

/**
 * OpenWeather scheduler tasks
 */
@Service
public class OpenWeatherSchedulerImpl implements OpenWeatherScheduler {
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherSchedulerImpl.class);

    /**
     * Les villes sont filtrées par nombre d'habitants afin de limiter le nombre de requetes, afin de ne pas saturer notre clef api OpenWeather
     */
    @Value("${ow.schedule.request.limit}")
    private int HAB;
    @Autowired
    private ApiOpenWeatherService service;

    @Scheduled(cron = "${ow.schedule.cron.meteo.current}")
    @Override
    public void fetchLocalWeatherForTopCitiesEveryHour() {
        List<Commune> communes = service.getCommunesByNbHab(HAB);
        LocalDateTime timeStamp = getTimeStamp();
        logger.info("Scheduled task running at {} for : Requesting and persistence of weather measurements for the hour for cities above {} inhabitants", timeStamp, HAB);
        for (Commune commune : communes) {
            try {
                service.requestAndSaveCurrentForecast(commune, timeStamp);
            } catch (FunctionnalException | ExternalApiResponseException | UnnecessaryApiRequestException e) {
                logger.debug("Current weather data error : Failed to get weather data for {}, with error : {}", commune.getNomSimple(), e.getMessage());
            }
        }
        logger.info("Scheduled task finished at {} for : Requesting and persistence of weather measurements for the hour for cities above {} inhabitants", LocalDateTime.now(), HAB);
    }

    @Scheduled(cron = "${ow.schedule.cron.meteo.fivedays}")
    @Override
    public void fetchFiveDayForecastEveryMorning() throws UnnecessaryApiRequestException, FunctionnalException {
        LocalDateTime timeStamp = getTimeStamp();

        logger.info("Scheduled task running at {} : Fetching 5 days forecasts", timeStamp);
        logger.info("Cleaning up old forecasts..");
        service.deleteOldForecasts(timeStamp);
        logger.info("Deletion completed");

        List<Commune> communes = service.getCommunesByNbHab(HAB);

        for (Commune commune : communes) {
            try {
                service.requestFiveDayForecast(commune, timeStamp);
            } catch (ExternalApiResponseException e) {
                logger.debug("Failed to get weather data for {}, with error : {}", commune.getNomSimple(), e.getMessage());
            }
        }
        logger.info("Scheduled task finished at {} for : Requesting and persistence of weather 5 days forecast for cities above {} inhabitants", LocalDateTime.now(), HAB);
    }

    @Scheduled(cron = "${ow.schedule.cron.meteo.air}")
    @Override
    public void fetchLocalAirDataForTopCitiesEveryHour() {
        List<Commune> communes = service.getCommunesByNbHab(HAB);
        LocalDateTime timeStamp = getTimeStamp();
        logger.info("Scheduled task running at {} for : Requesting and persistence of air measurements for the hour for cities above {} inhabitants", timeStamp, HAB);
        for (Commune commune : communes) {
            try {
                service.requestLocalAirQuality(commune, timeStamp);
            } catch (ExternalApiResponseException | UnnecessaryApiRequestException e) {
                logger.debug("Current weather data error : Failed to get Air data for {}, with error : {}", commune.getNomSimple(), e.getMessage());
            }
        }
        logger.info("Scheduled task finished at {} for : Requesting and persistence of air measurements for the hour for cities above {} inhabitants", LocalDateTime.now(), HAB);
    }
}
