package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.mapper.MesurePopulationMapper;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MesurePopulationServiceImplTest {
    
    @Mock private MesurePopulationRepository repository;
    @Mock private MesurePopulationMapper mapper;
    
    @InjectMocks private MesurePopulationServiceImpl service;
    
    private MesurePopulation mesure;
    private LocalDate date;
    private LocalDateTime dateTime;
    
    @BeforeEach
    void setup() {
        mesure = new MesurePopulation();
        date = LocalDate.of(2025, 1, 1);
        dateTime = date.atStartOfDay();
    }
    
    // --- save ---
    @Test
    void testSave_delegatesToRepository() {
        when(repository.save(mesure)).thenReturn(mesure);
        
        MesurePopulation result = service.save(mesure);
        
        assertSame(mesure, result);
        verify(repository).save(mesure);
    }
    
    // --- saveAll ---
    @Test
    void testSaveAll_delegatesToRepository() {
        List<MesurePopulation> list = List.of(new MesurePopulation(), new MesurePopulation());
        
        service.saveAll(list);
        
        verify(repository).saveAll(list);
    }
    
    // --- existsByDate ---
    @Test
    void testExistsByDate_callsRepositoryWithCorrectDates() {
        LocalDateTime expectedStart = date.atStartOfDay();
        LocalDateTime expectedEnd = date.plusDays(1).atStartOfDay();
        when(repository.existsByDate(expectedStart, expectedEnd)).thenReturn(true);
        
        boolean result = service.existsByDate(date);
        
        assertTrue(result);
        verify(repository).existsByDate(expectedStart, expectedEnd);
    }
    
    // --- existByDateReleve ---
    @Test
    void testExistByDateReleve_delegatesCorrectly() {
        when(repository.existsMesurePopulationByMesure_DateReleve(date.atStartOfDay())).thenReturn(true);
        
        boolean result = service.existByDateReleve(date);
        
        assertTrue(result);
        verify(repository).existsMesurePopulationByMesure_DateReleve(date.atStartOfDay());
    }
    
    // --- getAllByCodeInseeBetwenDates ---
    @Test
    void testGetAllByCodeInseeBetwenDates_delegatesAndMaps() {
        List<MesurePopulation> list = List.of(mesure);
        HistoriquePopulation expected = new HistoriquePopulation();
        
        when(repository.findAllByMesureCodeInseeAndMesureDateReleveBetween("12345", dateTime, dateTime)).thenReturn(list);
        when(mapper.toHistoricalDto(GeographicalScope.COMMUNE, "12345", list)).thenReturn(expected);
        
        HistoriquePopulation result = service.getAllByCodeInseeBetwenDates(
                GeographicalScope.COMMUNE, "12345", dateTime, dateTime);
        
        assertSame(expected, result);
    }
    
    // --- getAllByCodeRegionBetweenDates ---
    @Test
    void testGetAllByCodeRegionBetweenDates_delegatesAndMaps() {
        List<MesurePopulation> list = List.of(mesure);
        HistoriquePopulation expected = new HistoriquePopulation();
        
        when(repository.findAllByRegionAndDateReleveBetween(dateTime, dateTime, 11)).thenReturn(list);
        when(mapper.toHistoricalDtoFromRegion(GeographicalScope.REGION, "11", list)).thenReturn(expected);
        
        HistoriquePopulation result = service.getAllByCodeRegionBetweenDates(
                GeographicalScope.REGION, "11", dateTime, dateTime);
        
        assertSame(expected, result);
    }
    
    // --- getAllByCodeDepartementBetweenDates ---
    @Test
    void testGetAllByCodeDepartementBetweenDates_delegatesAndMaps() {
        List<MesurePopulation> list = List.of(mesure);
        HistoriquePopulation expected = new HistoriquePopulation();
        
        when(repository.findAllByDepartementAndDateReleveBetween(dateTime, dateTime, "44")).thenReturn(list);
        when(mapper.toHistoricalDtoFromDepartement(GeographicalScope.DEPARTEMENT, "44", list)).thenReturn(expected);
        
        HistoriquePopulation result = service.getAllByCodeDepartementBetweenDates(
                GeographicalScope.DEPARTEMENT, "44", dateTime, dateTime);
        
        assertSame(expected, result);
    }
}