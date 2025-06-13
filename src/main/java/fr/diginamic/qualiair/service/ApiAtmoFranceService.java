package fr.diginamic.qualiair.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.qualiair.dto.insertion.DailyAirDataDto;
import fr.diginamic.qualiair.entity.api.ApiAtmoFrance;
import fr.diginamic.qualiair.entity.api.ApiAtmoFranceToken;
import fr.diginamic.qualiair.entity.api.ApiToken;
import fr.diginamic.qualiair.entity.api.UtilisateurAtmoFrance;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.validator.AtmoFranceTokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

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
    @Value("${local.temp.directory}")
    private Path tempDir;


    public ApiAtmoFranceToken requestToken() throws ExternalApiResponseException {
        ApiAtmoFranceToken api = new ApiAtmoFranceToken();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UtilisateurAtmoFrance user = apiAtmoFrance.getUtilisateur();
            URI loginUri = apiAtmoFrance.getUriLogin();

            HttpEntity<UtilisateurAtmoFrance> requestEntity = new HttpEntity<>(user, headers);

            ResponseEntity<ApiToken> response = restTemplate.exchange(
                    loginUri,
                    HttpMethod.POST,
                    requestEntity,
                    ApiToken.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {

                if (response.getBody() == null) {
                    throw new ExternalApiResponseException("Response body is empty");
                }
                String token = response.getBody().getToken();
                api.setToken(token);

            } else {
                throw new ExternalApiResponseException("Status: " + response.getStatusCode() + "\n" + response.getBody());
            }
        } catch (RestClientException ex) {
            throw new ExternalApiResponseException("API error: " + ex.getMessage());
        }
        return api;
    }


    public void loadDailyFranceAirQualityData(String date) throws ExternalApiResponseException {
        try {
            Files.createDirectories(tempDir);
        } catch (IOException e) {
            throw new ExternalApiResponseException("Failed to create temp directory");
        }
        Path filePath = tempDir.resolve("atmo-" + date + ".json");

        if (Files.exists(filePath)) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        DailyAirDataDto rawJson = fetchDailyAirDataFromApi(date);

        try {
            objectMapper.writeValue(new File(filePath.toString()), rawJson);
        } catch (IOException e) {
            throw new ExternalApiResponseException("Failed to write response to file");
        }
        //todo DTO parsing impl from file
    }

    private DailyAirDataDto fetchDailyAirDataFromApi(String date) throws ExternalApiResponseException {
        if (!isTokenValid()) {
            throw new ExternalApiResponseException("Error refreshing token");
        }
        URI fullUri = UriComponentsBuilder.fromUri(apiAtmoFrance.getUriAirQuality()).queryParam("date", date).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiAtmoFrance.getToken().getToken());
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<DailyAirDataDto> response = restTemplate.exchange(fullUri, HttpMethod.GET, requestEntity, DailyAirDataDto.class);

        return response.getBody();
    }


    private boolean isTokenValid() throws ExternalApiResponseException {
        try {
            validator.validate(apiAtmoFrance.getToken());
        } catch (TokenExpiredException | BusinessRuleException e) {
            apiAtmoFrance.setToken(requestToken());
        }
        return true;
    }
}
