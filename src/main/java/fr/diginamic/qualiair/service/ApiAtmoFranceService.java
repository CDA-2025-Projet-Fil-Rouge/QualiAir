package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.api.AtmoFranceToken;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface ApiAtmoFranceService {
    /**
     * Sends a post request to AtmoFranceApi for a new bearer token
     *
     * @return new token timestamped at obtention
     * @throws ExternalApiResponseException the request failed
     */
    AtmoFranceToken requestToken() throws ExternalApiResponseException;

    /**
     * Sauvegarde les données de qualité de l'air pour la France à une date donnée.
     * Un check préalable est effectué afin de s'assurer que la requete n'a pas déjà éte executée pour cette date.
     * Sinon, un appel est fait pour recuperer les données et celles-ci sont mappées et persistées.
     *
     * @param date target date pour la requete
     * @throws ExternalApiResponseException   La connexion vers l'api a échoué
     * @throws UnnecessaryApiRequestException La requete à déjà été executée pour cette date
     */
    @Transactional
    void saveDailyFranceAirQualityData(String date, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException;
}
