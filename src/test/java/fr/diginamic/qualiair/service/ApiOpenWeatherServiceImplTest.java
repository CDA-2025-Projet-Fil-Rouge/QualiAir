package fr.diginamic.qualiair.service;


import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.entity.api.ApiOpenWeather;
import fr.diginamic.qualiair.exception.*;
import fr.diginamic.qualiair.factory.MesurePrevisionFactory;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiOpenWeatherServiceImplTest {
    
    @Mock private ApiOpenWeather api;
    @Mock private RestTemplate restTemplate;
    @Mock private HttpResponseValidator responseValidator;
    @Mock private MesurePrevisionService mesurePrevisionService;
    @Mock private MesurePrevisionFactory factory;
    @Mock private CommuneService communeService;
    @Mock private MesureAirService mesureAirService;
    @Mock private MesureAirMapper mesureAirMapper;
    
    @InjectMocks private ApiOpenWeatherServiceImpl service;
    
    private Commune mockCommune(double lat, double lon, String code) {
        Commune c = new Commune();
        c.setCodeInsee(code);
        Coordonnee coord = new Coordonnee();
        coord.setLatitude(lat);
        coord.setLongitude(lon);
        c.setCoordonnee(coord);
        return c;
    }
    
    @Test
    void testRequestAndSaveCurrentForecast_happyPath() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Commune commune = mockCommune(44.0, 3.0, "12345");
        when(mesurePrevisionService.existsByHourAndCodeInsee(any(), any(), any(), any()))
                .thenReturn(false);
        
        URI uri = URI.create("http://example.com/current");
        when(api.getUriCurrentWeather()).thenReturn(uri);
        when(api.getTokenParam()).thenReturn("appid");
        when(api.getToken()).thenReturn("fakeToken");
        
        ResponseEntity<CurrentForecastDto> response = new ResponseEntity<>(new CurrentForecastDto(), HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(CurrentForecastDto.class)))
                .thenReturn(response);
        when(factory.getInstanceList(any(), any(), any())).thenReturn(List.of(new MesurePrevision()));
        
        List<MesurePrevision> result = service.requestAndSaveCurrentForecast(commune, now);
        
        assertEquals(1, result.size());
        verify(mesurePrevisionService).saveMesurePrevision(any());
    }
    
    @Test
    void testRequestAndSaveCurrentForecast_alreadyExists() {
        LocalDateTime now = LocalDateTime.now();
        Commune commune = mockCommune(44.0, 3.0, "12345");
        when(mesurePrevisionService.existsByHourAndCodeInsee(any(), any(), any(), any()))
                .thenReturn(true);
        
        assertThrows(UnnecessaryApiRequestException.class,
                () -> service.requestAndSaveCurrentForecast(commune, now));
    }
    
    @Test
    void testRequestFiveDayForecast_happyPath() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Commune commune = mockCommune(48.0, 2.0, "75000");
        when(mesurePrevisionService.existsForTodayByTypeReleveAndCodeInsee(any(), any(), any()))
                .thenReturn(false);
        
        when(api.getUriWeather5Days()).thenReturn(URI.create("http://example.com/5d"));
        when(api.getTokenParam()).thenReturn("appid");
        when(api.getToken()).thenReturn("fake");
        when(factory.getInstanceList(any(), any(), any()))
                .thenReturn(List.of(new MesurePrevision()));
        
        ResponseEntity<ForecastFiveDayDto> response = new ResponseEntity<>(new ForecastFiveDayDto(), HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(ForecastFiveDayDto.class))).thenReturn(response);
        
        List<MesurePrevision> result = service.requestFiveDayForecast(commune, now);
        
        assertEquals(1, result.size());
        verify(mesurePrevisionService).saveMesurePrevision(any());
    }
    
    @Test
    void testRequestSixteenDaysForecast_happyPath() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Commune commune = mockCommune(50.0, 3.0, "59000");
        when(mesurePrevisionService.existsByHourAndCodeInsee(any(), any(), any(), any()))
                .thenReturn(false);
        
        when(api.getUriWeather16Days()).thenReturn(URI.create("http://example.com/16d"));
        when(api.getTokenParam()).thenReturn("appid");
        when(api.getToken()).thenReturn("tok");
        when(factory.getInstanceList(any(), any(), any()))
                .thenReturn(List.of(new MesurePrevision()));
        
        ResponseEntity<ForecastSixteenDays> response = new ResponseEntity<>(new ForecastSixteenDays(), HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(ForecastSixteenDays.class))).thenReturn(response);
        
        List<MesurePrevision> result = service.requestSixteenDaysForecast(commune, now);
        
        assertEquals(1, result.size());
        verify(mesurePrevisionService).saveMesurePrevision(any());
    }
    
    @Test
    void testRequestLocalAirQuality_happyPath() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Commune commune = mockCommune(45.0, 4.0, "69000");
        when(mesureAirService.existsByHour(any(), any(), any())).thenReturn(false);
        when(api.getUriLocalAirData()).thenReturn(URI.create("http://example.com/air"));
        when(api.getTokenParam()).thenReturn("appid");
        when(api.getToken()).thenReturn("key123");
        
        ResponseEntity<LocalAirQualityDto> response = new ResponseEntity<>(new LocalAirQualityDto(), HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(LocalAirQualityDto.class)))
                .thenReturn(response);
        when(mesureAirMapper.toEntityListFromOpenWeatherApi(any(), any(), any()))
                .thenReturn(List.of(new MesureAir()));
        
        List<MesureAir> result = service.requestLocalAirQuality(commune, now);
        
        assertEquals(1, result.size());
        verify(mesureAirService).saveMesureList(any());
    }
    
    @Test
    void testGetCommunesByNbHab() {
        when(communeService.getListTopCommunesByPopulation(100)).thenReturn(List.of(new Commune()));
        List<Commune> result = service.getCommunesByNbHab(100);
        assertEquals(1, result.size());
    }
    
    @Test
    void testDeleteOldForecasts() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        service.deleteOldForecasts(now);
        verify(mesurePrevisionService).deleteByTypeReleve(TypeReleve.PREVISION_5J);
    }
}