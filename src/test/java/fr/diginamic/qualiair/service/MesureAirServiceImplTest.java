package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.repository.MesureAirRepository;
import fr.diginamic.qualiair.repository.MesureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MesureAirServiceImplTest {
    
    @Mock private MesureAirRepository repository;
    @Mock private MesureAirRepository mesureAirRepository; // the extra one in the class
    @Mock private MesureAirMapper mapper;
    @Mock private MesureRepository mesureRepository;
    
    @InjectMocks private MesureAirServiceImpl service;
    
    private MesureAir mesure;
    private LocalDateTime now;
    
    @BeforeEach
    void setup() {
        mesure = new MesureAir();
        now = LocalDateTime.now();
    }
    
    // --- save ---
    @Test
    void testSave_delegatesToRepository() {
        when(repository.save(mesure)).thenReturn(mesure);
        
        MesureAir result = service.save(mesure);
        
        assertSame(mesure, result);
        verify(repository).save(mesure);
    }
    
    // --- existsByDateReleve ---
    @Test
    void testExistsByDateReleve_delegatesCorrectly() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        when(repository.existsMesureAirByMesure_DateReleve(date.atStartOfDay())).thenReturn(true);
        
        boolean result = service.existsByDateReleve(date);
        
        assertTrue(result);
        verify(repository).existsMesureAirByMesure_DateReleve(date.atStartOfDay());
    }
    
    // --- findWithDetailsByTypeAndIndiceLessThan ---
    @Test
    void testFindWithDetailsByTypeAndIndiceLessThan_delegates() {
        Page<MesureAir> page = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(repository.findWithDetailsByTypeAndIndiceLessThan("NO2", 5, pageable)).thenReturn(page);
        
        Page<MesureAir> result = service.findWithDetailsByTypeAndIndiceLessThan(AirPolluant.NO2, 5, pageable);
        
        assertSame(page, result);
        verify(repository).findWithDetailsByTypeAndIndiceLessThan("NO2", 5, pageable);
    }
    
    // --- saveMesureList ---
    @Test
    void testSaveMesureList_savesAll() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        List<MesureAir> input = List.of(new MesureAir(), new MesureAir());
        
        List<MesureAir> result = service.saveMesureList(input);
        
        assertEquals(2, result.size());
        verify(repository, times(2)).save(any());
    }
    
    // --- existsByHour ---
    @Test
    void testExistsByHour_usesTruncatedTimestamps() {
        when(repository.existsMesureAirByCodeInseeAndDateReleveBetween(any(), any(), any()))
                .thenReturn(true);
        
        boolean result = service.existsByHour("12345", now, now.plusHours(1));
        
        assertTrue(result);
        verify(repository).existsMesureAirByCodeInseeAndDateReleveBetween(eq("12345"),
                any(LocalDateTime.class), any(LocalDateTime.class));
    }
    
    // --- getAllByPolluantAndCodeInseeBetweenDates ---
    @Test
    void testGetAllByPolluantAndCodeInseeBetweenDates_normalPolluant() {
        List<MesureAir> list = List.of(new MesureAir());
        HistoriqueAirQuality expected = new HistoriqueAirQuality();
        
        when(repository.getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates("NO2", "12345", now, now))
                .thenReturn(list);
        when(mapper.toHistoriqueDto(GeographicalScope.COMMUNE, "12345", AirPolluant.NO2, list))
                .thenReturn(expected);
        
        HistoriqueAirQuality result = service.getAllByPolluantAndCodeInseeBetweenDates(
                GeographicalScope.COMMUNE, "12345", AirPolluant.NO2, now, now);
        
        assertSame(expected, result);
    }
    
    @Test
    void testGetAllByPolluantAndCodeInseeBetweenDates_pm25Case() {
        List<MesureAir> list = List.of(new MesureAir());
        when(repository.getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates("PM2.5", "C1", now, now))
                .thenReturn(list);
        when(mapper.toHistoriqueDto(any(), any(), any(), any())).thenReturn(new HistoriqueAirQuality());
        
        service.getAllByPolluantAndCodeInseeBetweenDates(
                GeographicalScope.COMMUNE, "C1", AirPolluant.PM25, now, now);
        
        verify(repository).getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates("PM2.5", "C1", now, now);
    }
    
    // --- getAllByPolluantAndCodeRegionBetweenDates ---
    @Test
    void testGetAllByPolluantAndCodeRegionBetweenDates_normalPolluant() {
        List<MesureAir> list = List.of(new MesureAir());
        HistoriqueAirQuality expected = new HistoriqueAirQuality();
        
        when(mesureAirRepository.findAllByRegionAndDateReleveBetween("NO2", now, now, 12))
                .thenReturn(list);
        when(mapper.toHistoriqueDtoFromRegion(GeographicalScope.REGION, "12", AirPolluant.NO2, list))
                .thenReturn(expected);
        
        HistoriqueAirQuality result = service.getAllByPolluantAndCodeRegionBetweenDates(
                GeographicalScope.REGION, "12", AirPolluant.NO2, now, now);
        
        assertSame(expected, result);
    }
    
    @Test
    void testGetAllByPolluantAndCodeRegionBetweenDates_pm25Case() {
        List<MesureAir> list = List.of(new MesureAir());
        when(mesureAirRepository.findAllByRegionAndDateReleveBetween("PM2.5", now, now, 99))
                .thenReturn(list);
        when(mapper.toHistoriqueDtoFromRegion(any(), any(), any(), any())).thenReturn(new HistoriqueAirQuality());
        
        service.getAllByPolluantAndCodeRegionBetweenDates(
                GeographicalScope.REGION, "99", AirPolluant.PM25, now, now);
        
        verify(mesureAirRepository).findAllByRegionAndDateReleveBetween("PM2.5", now, now, 99);
    }
    
    // --- getAllByPolluantAndCodeDepartementBetweenDates ---
    @Test
    void testGetAllByPolluantAndCodeDepartementBetweenDates_normalPolluant() {
        List<MesureAir> list = List.of(new MesureAir());
        HistoriqueAirQuality expected = new HistoriqueAirQuality();
        
        when(mesureAirRepository.findAllByDepartementAndDateReleveBetween("NO2", now, now, "33"))
                .thenReturn(list);
        when(mapper.toHistoriqueDtoFromDepartement(GeographicalScope.DEPARTEMENT, "33", AirPolluant.NO2, list))
                .thenReturn(expected);
        
        HistoriqueAirQuality result = service.getAllByPolluantAndCodeDepartementBetweenDates(
                GeographicalScope.DEPARTEMENT, "33", AirPolluant.NO2, now, now);
        
        assertSame(expected, result);
    }
    
    @Test
    void testGetAllByPolluantAndCodeDepartementBetweenDates_pm25Case() {
        List<MesureAir> list = List.of(new MesureAir());
        when(mesureAirRepository.findAllByDepartementAndDateReleveBetween("PM2.5", now, now, "44"))
                .thenReturn(list);
        when(mapper.toHistoriqueDtoFromDepartement(any(), any(), any(), any())).thenReturn(new HistoriqueAirQuality());
        
        service.getAllByPolluantAndCodeDepartementBetweenDates(
                GeographicalScope.DEPARTEMENT, "44", AirPolluant.PM25, now, now);
        
        verify(mesureAirRepository).findAllByDepartementAndDateReleveBetween("PM2.5", now, now, "44");
    }
}