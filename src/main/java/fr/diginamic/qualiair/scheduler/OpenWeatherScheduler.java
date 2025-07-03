package fr.diginamic.qualiair.scheduler;

import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import org.springframework.scheduling.annotation.Scheduled;

public interface OpenWeatherScheduler {
    /**
     * Charge en base la meteo  pour chaque ville (de plus de X habitants) à chaque heure, 1 minute et 10 secondes entre 06h et 22h
     */
    @Scheduled(cron = "${ow.schedule.cron.meteo.current}")
    void fetchLocalWeatherForTopCitiesEveryHour() throws UnnecessaryApiRequestException;

    /**
     * Charge en base les prévisions météo à 5 jours pour chaque ville (de plus de X habitants),
     * chaque matin à 6h31:10
     */
    @Scheduled(cron = "${ow.schedule.cron.meteo.fivedays}")
    void fetchFiveDayForecastEveryMorning() throws UnnecessaryApiRequestException;

    /**
     * Charge en base les relevé air toutes les heures pour chaque ville de plus de X habitants
     */
    @Scheduled(cron = "10 5 6-22 * * *")
    void fetchLocalAirDataForTopCitiesEveryHour() throws UnnecessaryApiRequestException, ExternalApiResponseException;
}
