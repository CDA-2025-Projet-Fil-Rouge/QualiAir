package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeature;
import fr.diginamic.qualiair.dto.atmofrance.DailyAirDataDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.entity.api.ApiAtmoFrance;
import fr.diginamic.qualiair.entity.api.ApiAtmoFranceToken;
import fr.diginamic.qualiair.entity.api.ApiToken;
import fr.diginamic.qualiair.entity.api.UtilisateurAtmoFrance;
import fr.diginamic.qualiair.exception.*;
import fr.diginamic.qualiair.mapper.CoordonneeMapper;
import fr.diginamic.qualiair.mapper.MesureMapper;
import fr.diginamic.qualiair.validator.AtmoFranceTokenValidator;
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
import java.util.List;

@Service
public class ApiAtmoFranceService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ApiAtmoFranceService.class);

    @Autowired
    private ApiAtmoFrance apiAtmoFrance;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AtmoFranceTokenValidator validator;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private MesureMapper mesureMapper;
    @Autowired
    private CoordonneeMapper coordonneeMapper;
    @Autowired
    private MesureAirService mesureAirService;
    @Autowired
    private CoordonneeService coordonneeService;


    /**
     * Sends a post request to AtmoFranceApi for a new bearer token
     *
     * @return new token timestamped at obtention
     * @throws ExternalApiResponseException the request failed
     */
    public ApiAtmoFranceToken requestToken() throws ExternalApiResponseException {
        ApiAtmoFranceToken api = new ApiAtmoFranceToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UtilisateurAtmoFrance user = apiAtmoFrance.getUtilisateur();
        URI loginUri = apiAtmoFrance.getUriLogin();

        HttpEntity<UtilisateurAtmoFrance> requestEntity = new HttpEntity<>(user, headers);

        ResponseEntity<ApiToken> response = restTemplate.exchange(loginUri, HttpMethod.POST, requestEntity, ApiToken.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ExternalApiResponseException("Error fetching new token for AtmoFrance Api, status: " + response.getStatusCode() + "\n" + response.getBody());
        }

        if (response.getBody() == null) {
            throw new ExternalApiResponseException("Error fetching new token for AtmoFrance Api, response body is empty");
        }

        String token = response.getBody().getToken();
        api.setToken(token);


        return api;
    }

    @Transactional
    public void saveDailyFranceAirQualityData(String date) throws ExternalApiResponseException, UnnecessaryApiRequestException {
        if (mesureAirService.existsByDate(date)) {
            throw new UnnecessaryApiRequestException(String.format("Measurements for date %s already exist, skipping", date));
        }
        cacheService.loadExistingCoordonnees();
        DailyAirDataDto responseDto = fetchDailyAirDataFromApi(date);

        for (AirDataFeature feature : responseDto.getFeatures()) {
            Coordonnee coordonnee;
            try {
                coordonnee = coordonneeMapper.toEntityFromAirDataTo(feature);
            } catch (ParsedDataException e) {
                logger.warn("Skipping Air quality registering for {} because : {}", feature.getProperties().getLibZone(), e.getMessage());
                continue;
            }
            Coordonnee savedCoordonnee = coordonneeService.findOrCreate(coordonnee);

            List<MesureAir> mesureAir = mesureMapper.toEntityFromAirDataDto(feature);

            mesureAir.forEach(mesure -> {
                mesure.setCoordonnee(savedCoordonnee);
                mesureAirService.save(mesure);
            });
        }

        cacheService.clearCaches();
    }

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


    private String getOrRefreshToken() throws ExternalApiResponseException {
        try {
            validator.validate(apiAtmoFrance.getToken());
        } catch (TokenExpiredException | BusinessRuleException e) {
            apiAtmoFrance.setToken(requestToken());
        }
        return apiAtmoFrance.getToken().getToken();
    }


}
