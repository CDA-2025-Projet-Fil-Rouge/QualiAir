package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.LocalAirQualityDto;
import fr.diginamic.qualiair.dto.openweather.fivedays.ForecastFiveDayDto;
import fr.diginamic.qualiair.entity.api.ApiOpenWeather;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ApiOpenWeatherService {

    private final MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    @Autowired
    private ApiOpenWeather api;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpResponseValidator responseValidator;

    public CurrentForecastDto requestCurrentForecast(double latitude, double longitude) throws ExternalApiResponseException {
        URI fullUri = getFullUri(api.getUriCurrentWeather(), latitude, longitude);

        ResponseEntity<CurrentForecastDto> response = restTemplate.getForEntity(fullUri, CurrentForecastDto.class);

        responseValidator.validate(response);

        return response.getBody();
    }

    public ForecastFiveDayDto requestFiveDayForecast(double latitude, double longitude) throws ExternalApiResponseException {
        URI fullUri = getFullUri(api.getUriWeather5Days(), latitude, longitude);

        ResponseEntity<ForecastFiveDayDto> response = restTemplate.getForEntity(fullUri, ForecastFiveDayDto.class);

        responseValidator.validate(response);

        return response.getBody();
    }

    public LocalAirQualityDto requestLocalAirQuality(double latitude, double longitude) throws ExternalApiResponseException {
        URI fullUri = getFullUri(api.getUriLocalAirData(), latitude, longitude);

        ResponseEntity<LocalAirQualityDto> response = restTemplate.getForEntity(fullUri, LocalAirQualityDto.class);

        responseValidator.validate(response);

        return response.getBody();
    }

    private URI getFullUri(URI sourceUri, double latitude, double longitude) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("lat", String.valueOf(latitude));
        queryParams.add("lon", String.valueOf(longitude));
        queryParams.add(api.getTokenParam(), api.getToken());

        return UriComponentsBuilder.fromUri(sourceUri).queryParams(queryParams).build().toUri();
    }
}
