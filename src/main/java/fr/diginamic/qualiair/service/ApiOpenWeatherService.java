package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiOpenWeatherService {
    /**
     * Récupère les coordonnées de la ville ciblée afin de construire l'uri de la requete, effectuée ensuite un appel pour executer la requete
     *
     * @param commune nom de la ville cible pour la requete externe
     * @return liste de mesures
     * @throws ExternalApiResponseException   api injoignable
     * @throws UnnecessaryApiRequestException requete inutile
     * @throws FunctionnalException           ville absente en base
     */
    @Transactional
    List<MesurePrevision> requestAndSaveCurrentForecast(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException, FunctionnalException;

    /**
     * Récupère les coordonnées de la ville ciblée afin de construire l'uri de la requete, effectuée ensuite un appel pour executer la requete
     *
     * @param commune nom de la ville cible pour la requete externe
     * @return liste de mesures
     * @throws ExternalApiResponseException   api injoignable
     * @throws UnnecessaryApiRequestException requete inutile
     */
    @Transactional
    List<MesurePrevision> requestFiveDayForecast(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException;

    /**
     * Récupère les coordonnées de la ville ciblée afin de construire l'uri de la requete, effectuée ensuite un appel pour executer la requete
     *
     * @param commune nom de la ville cible pour la requete externe
     * @return liste de mesures
     * @throws ExternalApiResponseException   api injoignable
     * @throws UnnecessaryApiRequestException requete inutile
     */
    @Transactional
    List<MesurePrevision> requestSixteenDaysForecast(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException;

    List<MesureAir> requestLocalAirQuality(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException;

    List<Commune> getCommunesByNbHab(int hab);

    void deleteOldForecasts(LocalDateTime timeStamp) throws FunctionnalException;
}
