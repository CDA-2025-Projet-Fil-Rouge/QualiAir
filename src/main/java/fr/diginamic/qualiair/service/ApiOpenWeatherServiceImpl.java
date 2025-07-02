package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.entity.api.ApiOpenWeather;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.factory.MesurePrevisionFactory;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.utils.MesureUtils;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static fr.diginamic.qualiair.utils.MesureUtils.throwIfExists;

/**
 * Service dédié aux opérations sortantes vers l'api Open Weather.
 * Les méthodes et URI pris en charges sont les requetes meteo 1h, les prévision court terme (5j) et long terme (16j)
 */
@Service
public class ApiOpenWeatherServiceImpl implements ApiOpenWeatherService {
    private static final Logger logger = LoggerFactory.getLogger(ApiOpenWeatherServiceImpl.class);
    @Autowired
    private ApiOpenWeather api;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpResponseValidator responseValidator;
    @Autowired
    private MesurePrevisionService mesurePrevisionService;
    @Autowired
    private MesurePrevisionFactory factory;
    @Autowired
    private CommuneService communeService;
    @Autowired
    private MesureAirService mesureAirService;
    @Autowired
    private MesureAirMapper mesureAirMapper;

    /**
     * Methode générique pour les appels vers l'api OpenWeather
     *
     * @param uri          uri complet de la requete
     * @param responseType DTO cible de la réponse serveur
     * @param <T>          La classe mère des dtos
     * @return une liste de mesures sauvegargées en base
     * @throws ExternalApiResponseException La connexion vers l'api a échouée
     */
    private <T extends OpenWeatherForecastDto> List<MesurePrevision> fetchAndSaveForecast(URI uri, Class<T> responseType, Coordonnee coordonnee, LocalDateTime timeStamp) throws ExternalApiResponseException {
        ResponseEntity<T> response = restTemplate.getForEntity(uri, responseType);
        responseValidator.validate(response);

        T dto = response.getBody();

        List<MesurePrevision> mesures = factory.getInstanceList(dto, timeStamp);

        mesures.forEach(mesure -> mesure.setCoordonnee(coordonnee));
        mesurePrevisionService.saveMesurePrevision(mesures);

        return mesures;
    }

    @Transactional
    @Override
    public List<MesurePrevision> requestAndSaveCurrentForecast(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException {

        String codeInsee = commune.getCodeInsee();


        LocalDateTime endDate = timeStamp.plusHours(1).minusNanos(1);
        TypeReleve typeReleve = TypeReleve.ACTUEL;

        boolean exists = mesurePrevisionService.existsByHourAndCodeInsee(timeStamp, endDate, typeReleve, codeInsee);

        MesureUtils.throwIfExists(exists, timeStamp, endDate, typeReleve);
        Coordonnee coordonnee = commune.getCoordonnee();

        URI uri = getFullUri(api.getUriCurrentWeather(), coordonnee.getLatitude(), coordonnee.getLongitude());

        return fetchAndSaveForecast(uri, CurrentForecastDto.class, coordonnee, timeStamp);
    }

    @Transactional
    @Override
    public List<MesurePrevision> requestFiveDayForecast(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException {

        String codeInsee = commune.getCodeInsee();
        LocalDateTime endDate = timeStamp.plusDays(1);
        TypeReleve typeReleve = TypeReleve.PREVISION_5J;

        boolean exists = mesurePrevisionService.existsForTodayByTypeReleveAndCodeInsee(timeStamp, TypeReleve.PREVISION_5J, codeInsee);
        throwIfExists(exists, timeStamp, typeReleve);

        Coordonnee coordonnee = commune.getCoordonnee();

        URI uri = getFullUri(api.getUriWeather5Days(), coordonnee.getLatitude(), coordonnee.getLongitude());

        return fetchAndSaveForecast(uri, ForecastFiveDayDto.class, coordonnee, timeStamp);
    }

    @Transactional
    @Override
    public List<MesurePrevision> requestSixteenDaysForecast(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException {

        String codeInsee = commune.getCodeInsee();

        LocalDateTime endDate = timeStamp.plusDays(1);
        TypeReleve typeReleve = TypeReleve.PREVISION_16J;
        boolean exists = mesurePrevisionService.existsByHourAndCodeInsee(timeStamp, endDate, TypeReleve.PREVISION_16J, codeInsee);
        MesureUtils.throwIfExists(exists, timeStamp, endDate, typeReleve);

        Coordonnee coordonnee = commune.getCoordonnee();

        URI uri = getFullUri(api.getUriWeather16Days(), coordonnee.getLatitude(), coordonnee.getLongitude());

        return fetchAndSaveForecast(uri, ForecastSixteenDays.class, coordonnee, timeStamp);
    }


    @Override
    public List<MesureAir> requestLocalAirQuality(Commune commune, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException {
        LocalDateTime endDate = timeStamp.plusHours(1).minusNanos(1);
        String codeInsee = commune.getCodeInsee();
        boolean exists = mesureAirService.existsByHour(codeInsee, timeStamp, endDate);
        MesureUtils.throwIfExists(exists, timeStamp, endDate);

        Coordonnee coordonnee = commune.getCoordonnee();
        URI fullUri = getFullUri(api.getUriLocalAirData(), coordonnee.getLatitude(), coordonnee.getLongitude());
        ResponseEntity<LocalAirQualityDto> response = restTemplate.getForEntity(fullUri, LocalAirQualityDto.class);
        responseValidator.validate(response);

        LocalAirQualityDto dto = response.getBody();

        List<MesureAir> mesures = mesureAirMapper.toEntityList(dto, coordonnee, timeStamp);

        mesureAirService.saveMesureList(mesures);
        return mesures;
    }

    /**
     * Construit l'uri de la requete api
     *
     * @param sourceUri uri de base définit dans le fichier config
     * @param latitude  latitude
     * @param longitude longitude
     * @return uri complet
     */
    private URI getFullUri(URI sourceUri, double latitude, double longitude) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lat", String.valueOf(latitude));
        queryParams.add("lon", String.valueOf(longitude));
        queryParams.add("units", "metric");
        queryParams.add("lang", "fr");
        queryParams.add(api.getTokenParam(), api.getToken());

        return UriComponentsBuilder.fromUri(sourceUri).queryParams(queryParams).build().toUri();
    }

    @Override
    public List<Commune> getCommunesByNbHab(int hab) {
        return communeService.getListTopCommunesByPopulation(hab);
    }
}
