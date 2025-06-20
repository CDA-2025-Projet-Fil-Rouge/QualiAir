package fr.diginamic.qualiair.scheduler;

import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.service.ApiAtmoFranceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static fr.diginamic.qualiair.utils.DateUtils.getTimeStamp;
import static fr.diginamic.qualiair.utils.DateUtils.toStringSimplePattern;

/**
 * Scheduler pour l'api externe atmo-france
 */
@Service
public class AtmoFranceSchedulerImpl implements AtmoFranceScheduler {
    private static final Logger logger = LoggerFactory.getLogger(AtmoFranceSchedulerImpl.class);
    @Autowired
    private ApiAtmoFranceService service;


    @Scheduled(cron = "${atmo.schedule.cron.meteo}")
    @Override
    public void fetchAirQualityDataAndPersist() {
        LocalDateTime timeStamp = getTimeStamp();
        logger.info("Scheduled task running at {}", LocalDateTime.now());
        try {
            service.saveDailyFranceAirQualityData(toStringSimplePattern(timeStamp), timeStamp);
            logger.info("Executed automated persistence task for air quality data for date {}.", timeStamp);
        } catch (ExternalApiResponseException | UnnecessaryApiRequestException e) {
            logger.info("Failed automated persistence task for air quality data for date {}.", timeStamp);
            throw new RuntimeException(e);
        }
        logger.info("Scheduled task fininished at {}", LocalDateTime.now());
    }
}
