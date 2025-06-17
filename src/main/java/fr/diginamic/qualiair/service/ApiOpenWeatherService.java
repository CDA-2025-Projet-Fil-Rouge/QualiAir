package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.entity.api.ApiOpenWeather;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.factory.MesurePrevisionFactory;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static fr.diginamic.qualiair.utils.MesureUtils.ThrowExceptionIfTrue;

@Service
public class ApiOpenWeatherService {
    private static final Logger logger = LoggerFactory.getLogger(ApiOpenWeatherService.class);
    @Autowired
    private ApiOpenWeather api;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpResponseValidator responseValidator;
    @Autowired
    private MesurePrevisionService service;
    @Autowired
    private MesurePrevisionFactory factory;
    @Autowired
    private CommuneService communeService;

    /**
     * Methode générique pour les appels vers l'api OpenWeather
     *
     * @param uri          uri complet de la requete
     * @param responseType DTO cible de la réponse serveur
     * @param <T>          La classe mère des dtos
     * @return une liste de mesures sauvegargées en base
     * @throws ExternalApiResponseException La connexion vers l'api a échouée
     * @throws ParsedDataException          erreur de conversion de données
     */
    private <T extends OpenWeatherForecastDto> List<MesurePrevision> fetchAndSaveForecast(URI uri, Class<T> responseType, Coordonnee coordonnee) throws ExternalApiResponseException, ParsedDataException {
        ResponseEntity<T> response = restTemplate.getForEntity(uri, responseType);
        responseValidator.validate(response);

        T dto = response.getBody();

        List<MesurePrevision> mesures = factory.getInstanceList(dto);
        mesures.forEach(mesure -> mesure.setCoordonnee(coordonnee));
        service.saveMesurePrevision(mesures);

        return mesures;
    }

    /**
     * Récupère les coordonnées de la ville ciblée afin de construire l'uri de la requete, effectuée ensuite un appel pour executer la requete
     *
     * @param commune nom de la ville cible pour la requete externe
     * @return liste de mesures
     * @throws ExternalApiResponseException   api injoignable
     * @throws UnnecessaryApiRequestException requete inutile
     * @throws FunctionnalException           ville absente en base
     * @throws ParsedDataException            erreur de conversion
     */
    public List<MesurePrevision> requestAndSaveCurrentForecast(Commune commune) throws ExternalApiResponseException, UnnecessaryApiRequestException, FunctionnalException, ParsedDataException {

        String nomPostal = commune.getNomPostal();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDate = startDate.plusHours(1).minusNanos(1);
        TypeReleve typeReleve = TypeReleve.ACTUEL;

        boolean exists = service.existsByHourAndNomPostal(startDate, endDate, typeReleve, nomPostal);

        ThrowExceptionIfTrue(exists, startDate, endDate, typeReleve);
        Coordonnee coordonnee = commune.getCoordonnee();

        URI uri = getFullUri(api.getUriCurrentWeather(), coordonnee.getLatitude(), coordonnee.getLongitude());

        return fetchAndSaveForecast(uri, CurrentForecastDto.class, coordonnee);
    }

    /**
     * Récupère les coordonnées de la ville ciblée afin de construire l'uri de la requete, effectuée ensuite un appel pour executer la requete
     *
     * @param commune nom de la ville cible pour la requete externe
     * @return liste de mesures
     * @throws ExternalApiResponseException   api injoignable
     * @throws UnnecessaryApiRequestException requete inutile
     * @throws ParsedDataException            erreur de conversion
     */
    public List<MesurePrevision> requestFiveDayForecast(Commune commune) throws ExternalApiResponseException, UnnecessaryApiRequestException, ParsedDataException {

        String nomPostal = commune.getNomPostal();

        LocalDateTime startDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDate = startDate.plusDays(1);
        TypeReleve typeReleve = TypeReleve.PREVISION_5J;

        boolean exists = service.existsByHourAndNomPostal(startDate, endDate, TypeReleve.PREVISION_5J, nomPostal);
        ThrowExceptionIfTrue(exists, startDate, endDate, typeReleve);

        Coordonnee coordonnee = commune.getCoordonnee();

        URI uri = getFullUri(api.getUriWeather5Days(), coordonnee.getLatitude(), coordonnee.getLongitude());

        return fetchAndSaveForecast(uri, ForecastFiveDayDto.class, coordonnee);
    }

    /**
     * Récupère les coordonnées de la ville ciblée afin de construire l'uri de la requete, effectuée ensuite un appel pour executer la requete
     *
     * @param commune nom de la ville cible pour la requete externe
     * @return liste de mesures
     * @throws ExternalApiResponseException   api injoignable
     * @throws UnnecessaryApiRequestException requete inutile
     * @throws ParsedDataException            erreur de conversion
     */
    public List<MesurePrevision> requestSixteenDaysForecast(Commune commune) throws ExternalApiResponseException, UnnecessaryApiRequestException, ParsedDataException {

        String nomPostal = commune.getNomPostal();

        LocalDateTime startDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDate = startDate.plusDays(1);
        TypeReleve typeReleve = TypeReleve.PREVISION_16J;
        boolean exists = service.existsByHourAndNomPostal(startDate, endDate, TypeReleve.PREVISION_16J, nomPostal);
        ThrowExceptionIfTrue(exists, startDate, endDate, typeReleve);

        Coordonnee coordonnee = commune.getCoordonnee();

        URI uri = getFullUri(api.getUriWeather16Days(), coordonnee.getLatitude(), coordonnee.getLongitude());

        return fetchAndSaveForecast(uri, ForecastSixteenDays.class, coordonnee);
    }


    public LocalAirQualityDto requestLocalAirQuality(double latitude, double longitude) throws ExternalApiResponseException { //todo a voir si on implemente
        URI fullUri = getFullUri(api.getUriLocalAirData(), latitude, longitude);
        ResponseEntity<LocalAirQualityDto> response = restTemplate.getForEntity(fullUri, LocalAirQualityDto.class);
        responseValidator.validate(response);
        return response.getBody();
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

    public List<Commune> getCommunesByNbHab(int hab) {
        return communeService.getListTopCommunesByPopulation(hab);
    }
}
