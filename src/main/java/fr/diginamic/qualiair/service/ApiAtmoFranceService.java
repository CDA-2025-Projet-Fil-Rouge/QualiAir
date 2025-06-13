package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.api.ApiAtmoFranceToken;
import fr.diginamic.qualiair.entity.api.UtilisateurAtmoFrance;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class ApiAtmoFranceService {
    @Value("${external.api.atmo.identifiant}")
    private String loginname;
    @Value("${external.api.atmo.mot-de-passe}")
    private String password;
    @Value("${external.api.atmo.uri.login}")
    private String loginUri;

    @Autowired
    private RestTemplate restTemplate;


    public ApiAtmoFranceToken requestToken() throws ExternalApiResponseException {
        ApiAtmoFranceToken api = new ApiAtmoFranceToken();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            UtilisateurAtmoFrance user = new UtilisateurAtmoFrance(loginname, password);
            HttpEntity<UtilisateurAtmoFrance> requestEntity = new HttpEntity<>(user, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    loginUri,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {

                String token = response.getHeaders().getFirst("Authorization");

                if (token == null) {
                    throw new ExternalApiResponseException("Authorization header missing in response");
                }

                api.setBearerToken(token);
                api.setLocalDateTimeTokenObtention(LocalDateTime.now());

            } else {
                throw new ExternalApiResponseException("Status: " + response.getStatusCode() + "\n" + response.getBody());
            }

        } catch (RestClientException ex) {
            throw new ExternalApiResponseException("API error: " + ex.getMessage());
        }
        return api;
    }


    public void loadDailyFranceAirQualityData(String date) {
    }
}
