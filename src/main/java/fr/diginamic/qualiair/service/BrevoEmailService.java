package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.config.BrevoEmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrevoEmailService {


    @Autowired
    private BrevoEmailConfig config;
    @Autowired
    private RestTemplate restTemplate;

    public void sendListingEmail(List<String> emails, String subject, String message) {
        URI uri = config.getSendEmailUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.set(config.getKeyAttribute(), config.getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = new HashMap<>();

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        restTemplate.postForEntity(uri, request, String.class);

    }

}
