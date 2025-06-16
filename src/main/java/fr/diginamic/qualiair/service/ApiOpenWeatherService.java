package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.entity.api.ApiOpenWeather;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.factory.MesurePrevisionFactory;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
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

    private final MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
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

    private <T extends OpenWeatherForecastDto> List<MesurePrevision> fetchAndSaveForecast(URI uri, Class<T> responseType) throws ExternalApiResponseException {

        ResponseEntity<T> response = restTemplate.getForEntity(uri, responseType);
        responseValidator.validate(response);

        T dto = response.getBody();

        List<MesurePrevision> mesures = factory.getInstanceList(dto);
        service.saveMesurePrevision(mesures);

        return mesures;
    }

    public List<MesurePrevision> requestAndSaveCurrentForecast(String nomPostal) throws ExternalApiResponseException, UnnecessaryApiRequestException, FunctionnalException {

        Commune commune = communeService.findByNomPostal(nomPostal);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDate = startDate.plusHours(1).minusNanos(1);
        TypeReleve typeReleve = TypeReleve.ACTUEL;

        boolean exists = service.existsByHourAndNomPostal(startDate, endDate, typeReleve, nomPostal);
        ThrowExceptionIfTrue(exists, startDate, endDate, typeReleve);

        double latitude = commune.getCoordonnee().getLatitude();
        double longitude = commune.getCoordonnee().getLongitude();
        URI uri = getFullUri(api.getUriCurrentWeather(), latitude, longitude);

        return fetchAndSaveForecast(uri, CurrentForecastDto.class);
    }


    public List<MesurePrevision> requestFiveDayForecast(String nomPostal) throws ExternalApiResponseException, FunctionnalException, UnnecessaryApiRequestException {

        Commune commune = communeService.findByNomPostal(nomPostal);

        LocalDateTime startDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDate = startDate.plusDays(5);
        TypeReleve typeReleve = TypeReleve.PREVISION_5J;

        boolean exists = service.existsByHourAndNomPostal(startDate, endDate, TypeReleve.PREVISION_5J, nomPostal);
        ThrowExceptionIfTrue(exists, startDate, endDate, typeReleve);

        double latitude = commune.getCoordonnee().getLatitude();
        double longitude = commune.getCoordonnee().getLongitude();
        URI fullUri = getFullUri(api.getUriWeather5Days(), latitude, longitude);
        return fetchAndSaveForecast(fullUri, ForecastFiveDayDto.class);
    }

    public List<MesurePrevision> requestSixteenDaysForecast(String nomPostal) throws ExternalApiResponseException, FunctionnalException, UnnecessaryApiRequestException {

        Commune commune = communeService.findByNomPostal(nomPostal);

        LocalDateTime startDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime endDate = startDate.plusDays(16);
        TypeReleve typeReleve = TypeReleve.PREVISION_16J;
        boolean exists = service.existsByHourAndNomPostal(startDate, endDate, TypeReleve.PREVISION_16J, nomPostal);
        ThrowExceptionIfTrue(exists, startDate, endDate, typeReleve);

        double latitude = commune.getCoordonnee().getLatitude();
        double longitude = commune.getCoordonnee().getLongitude();

        URI fullUri = getFullUri(api.getUriWeather5Days(), latitude, longitude);
        return fetchAndSaveForecast(fullUri, ForecastSixteenDays.class);
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
