package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.atmofrance.DailyAirDataDto;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.entity.api.ApiAtmoFrance;
import fr.diginamic.qualiair.entity.api.ApiToken;
import fr.diginamic.qualiair.entity.api.AtmoFranceToken;
import fr.diginamic.qualiair.entity.api.UtilisateurAtmoFrance;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.utils.CommuneUtils;
import fr.diginamic.qualiair.validator.AtmoFranceTokenValidator;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Service dédié aux opérations sortantes vers l'api Atmo France.
 * Les méthodes et URI pris en charges sont les requetes d'authentification pour récupérer le {@link AtmoFranceToken} et la demande de {@link MesureAir} pour toutes la France.
 */
@Service
public class ApiAtmoFranceServiceImpl implements ApiAtmoFranceService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ApiAtmoFranceServiceImpl.class);

    @Autowired
    private ApiAtmoFrance apiAtmoFrance;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AtmoFranceTokenValidator validator;
    @Autowired
    private MesureAirMapper mesureAirMapper;
    @Autowired
    private MesureAirService mesureAirService;
    @Autowired
    private HttpResponseValidator responseValidator;
    @Autowired
    private CommuneService communeService;


    @Override
    public AtmoFranceToken requestToken() throws ExternalApiResponseException {
        AtmoFranceToken api = new AtmoFranceToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UtilisateurAtmoFrance user = apiAtmoFrance.getUtilisateur();
        URI loginUri = apiAtmoFrance.getUriLogin();

        HttpEntity<UtilisateurAtmoFrance> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<ApiToken> response = restTemplate.exchange(loginUri, HttpMethod.POST, requestEntity, ApiToken.class);

        String token = responseValidator.validateAndReturnToken(response);

        api.setToken(token);

        return api;
    }

    @Transactional
    @Override
    public void saveDailyFranceAirQualityData(String date, LocalDateTime timeStamp) throws ExternalApiResponseException, UnnecessaryApiRequestException {
        if (mesureAirService.existsByDateReleve(timeStamp.toLocalDate())) {
            throw new UnnecessaryApiRequestException(String.format("Measurements for date %s already exist, skipping", timeStamp));
        }

        List<Commune> communes = communeService.getAllCommunesWithCoordinates();
        HashMap<String, Commune> map = new HashMap<>();
        communes.forEach(c -> map.put(c.getCodeInsee(), c));
        DailyAirDataDto responseDto = fetchDailyAirDataFromApi(date);
        List<AirDataFeatureDto> dataList = responseDto.getFeatures().stream().filter(f -> f.getProperties().getTypeZone().equalsIgnoreCase("commune")).toList();

        for (AirDataFeatureDto feature : dataList) {
            String featureCodeInsee = CommuneUtils.normalizeInseeCode(feature.getProperties().getCodeZone().trim());

            Commune commune = map.get(featureCodeInsee);
            if (commune == null) {
                logger.debug("Commune not found for {}", feature.getProperties().getLibZone());
                continue;
            }
            Coordonnee savedCoordonnee = commune.getCoordonnee();

            List<MesureAir> mesureAir = mesureAirMapper.toEntityListFromAtmoFranceApi(feature, timeStamp, savedCoordonnee);

            mesureAirService.saveMesureList(mesureAir);
        }
    }

    /**
     * Recupère les données air qualité pour la France depuis l'api Atmo-France. Le token d'authentification est placé de le header de la requete. Si le retour est status 200 l'objet réponse est retransmit.
     *
     * @param date date cible
     * @return dto réponse
     * @throws ExternalApiResponseException La connexion vers l'api a échoué
     */
    private DailyAirDataDto fetchDailyAirDataFromApi(String date) throws ExternalApiResponseException {
        String token = getOrRefreshToken();

        try {
            URI fullUri = UriComponentsBuilder.fromUri(apiAtmoFrance.getUriAirQuality()).queryParam("date", date).build().toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<DailyAirDataDto> response = restTemplate.exchange(fullUri, HttpMethod.GET, requestEntity, DailyAirDataDto.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ExternalApiResponseException("Status: " + response.getStatusCode() + "\n" + response.getBody());
            }

            if (response.getBody() == null) {
                throw new ExternalApiResponseException("Response body is empty");
            }
            return response.getBody();
        } catch (RestClientException ex) {
            throw new ExternalApiResponseException("API error: " + ex.getMessage());
        }
    }

    /**
     * Vérifie la validité du token d'identification et le renouvelle s'il est invalide (durée > 1h)
     *
     * @return le token valide
     * @throws ExternalApiResponseException La connexion vers l'api a échoué
     */
    private String getOrRefreshToken() throws ExternalApiResponseException {
        try {
            validator.validate(apiAtmoFrance.getToken());
        } catch (NullPointerException | TokenExpiredException | BusinessRuleException e) {
            apiAtmoFrance.setToken(requestToken());
        }
        return apiAtmoFrance.getToken().getToken();
    }


}
