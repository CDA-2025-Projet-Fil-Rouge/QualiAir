package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.atmofrance.*;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.entity.api.*;
import fr.diginamic.qualiair.exception.*;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.validator.*;
import org.junit.jupiter.api.BeforeEach;
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
class ApiAtmoFranceServiceImplTest {
    
    @Mock private ApiAtmoFrance apiAtmoFrance;
    @Mock private RestTemplate restTemplate;
    @Mock private AtmoFranceTokenValidator validator;
    @Mock private MesureAirMapper mesureAirMapper;
    @Mock private MesureAirService mesureAirService;
    @Mock private HttpResponseValidator responseValidator;
    @Mock private CommuneService communeService;
    
    @InjectMocks private ApiAtmoFranceServiceImpl service;
    
    @BeforeEach
    void setupDefaults() {
        // Basic default behavior for the token
        AtmoFranceToken token = new AtmoFranceToken();
        token.setToken("fake");
        lenient().when(apiAtmoFrance.getToken()).thenReturn(token);
    }
    
    @Test
    void testRequestToken() throws Exception {
        UtilisateurAtmoFrance user = new UtilisateurAtmoFrance();
        URI uri = URI.create("http://example.com/login");
        ApiToken apiToken = new ApiToken();
        apiToken.setToken("tok123");
        
        when(apiAtmoFrance.getUtilisateur()).thenReturn(user);
        when(apiAtmoFrance.getUriLogin()).thenReturn(uri);
        when(responseValidator.validateAndReturnToken(any())).thenReturn("abc123");
        
        ResponseEntity<ApiToken> resp = new ResponseEntity<>(apiToken, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST), any(HttpEntity.class), eq(ApiToken.class)))
                .thenReturn(resp);
        
        AtmoFranceToken result = service.requestToken();
        
        assertEquals("abc123", result.getToken());
    }
    
    @Test
    void testSaveDailyFranceAirQualityData_skipIfExists() {
        LocalDateTime now = LocalDateTime.now();
        when(mesureAirService.existsByDateReleve(now.toLocalDate())).thenReturn(true);
        
        assertThrows(UnnecessaryApiRequestException.class,
                () -> service.saveDailyFranceAirQualityData("2024-01-01", now));
    }
    
    @Test
    void testSaveDailyFranceAirQualityData_happyPath() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        when(mesureAirService.existsByDateReleve(now.toLocalDate())).thenReturn(false);
        
        Commune commune = new Commune();
        commune.setCodeInsee("12345");
        Coordonnee coord = new Coordonnee();
        commune.setCoordonnee(coord);
        
        when(communeService.getAllCommunesWithCoordinates()).thenReturn(List.of(commune));
        when(apiAtmoFrance.getUriAirQuality()).thenReturn(URI.create("http://example.com/air"));
        
        AirDataFeatureDto feature = new AirDataFeatureDto();
        AirDataPropertiesDto props = new AirDataPropertiesDto();
        props.setTypeZone("commune");
        props.setCodeZone("12345");
        props.setLibZone("ZoneX");
        feature.setProperties(props);
        
        DailyAirDataDto dto = new DailyAirDataDto();
        dto.setFeatures(List.of(feature));
        
        ResponseEntity<DailyAirDataDto> response = new ResponseEntity<>(dto, HttpStatus.OK);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(DailyAirDataDto.class)))
                .thenReturn(response);
        when(mesureAirMapper.toEntityListFromAtmoFranceApi(any(), any(), any()))
                .thenReturn(List.of(new MesureAir()));
        
        service.saveDailyFranceAirQualityData("2024-01-01", now);
        
        verify(mesureAirService).saveMesureList(any());
    }
    
    @Test
    void testTokenRefreshFlow() throws Exception {
        doThrow(new TokenExpiredException("expired")).when(validator).validate(any());
        when(apiAtmoFrance.getUtilisateur()).thenReturn(new UtilisateurAtmoFrance());
        when(apiAtmoFrance.getUriLogin()).thenReturn(URI.create("http://example.com/login"));
        when(responseValidator.validateAndReturnToken(any())).thenReturn("newToken");
        
        ResponseEntity<ApiToken> resp = new ResponseEntity<>(new ApiToken(), HttpStatus.OK);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(ApiToken.class)))
                .thenReturn(resp);
        
        // Indirectly calls getOrRefreshToken via reflection to hit that private path
        var method = ApiAtmoFranceServiceImpl.class.getDeclaredMethod("getOrRefreshToken");
        method.setAccessible(true);
        String result = (String) method.invoke(service);
        
        assertNotNull(result);
    }
}