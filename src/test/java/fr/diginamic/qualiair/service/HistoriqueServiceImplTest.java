package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.*;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.ExportException;
import fr.diginamic.qualiair.validator.HistoriqueValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoriqueServiceImplTest {
    
    @Mock private HistoriqueValidator validator;
    @Mock private MesurePrevisionService prevService;
    @Mock private MesurePopulationService popService;
    @Mock private MesureAirService airService;
    @Mock private CsvService csvService;
    
    @InjectMocks private HistoriqueServiceImpl service;
    
    private LocalDate startDate;
    private LocalDate endDate;
    
    @BeforeEach
    void setup() {
        startDate = LocalDate.of(2025, 1, 1);
        endDate = LocalDate.of(2025, 1, 5);
    }
    
    // ---- executePrevision ----
    @Test
    void testExecutePrevision_CommuneScope() {
        NatureMesurePrevision nature = NatureMesurePrevision.PRESSURE;
        HistoriquePrevision expected = new HistoriquePrevision();
        
        when(prevService.getAllByNatureAndCodeInseeBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriquePrevision result = service.executePrevision(
                GeographicalScope.COMMUNE, "12345", nature, startDate, endDate);
        
        assertSame(expected, result);
        verify(validator).validateParams(nature, "12345", startDate, endDate);
    }
    
    @Test
    void testExecutePrevision_RegionScope() {
        NatureMesurePrevision nature = NatureMesurePrevision.TEMPERATURE;
        HistoriquePrevision expected = new HistoriquePrevision();
        when(prevService.getAllByNatureAndCodeRegionBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriquePrevision result = service.executePrevision(
                GeographicalScope.REGION, "R1", nature, startDate, endDate);
        
        assertSame(expected, result);
    }
    
    @Test
    void testExecutePrevision_DepartementScope() {
        NatureMesurePrevision nature = NatureMesurePrevision.HUMIDITY;
        HistoriquePrevision expected = new HistoriquePrevision();
        when(prevService.getAllByNatureAndCodeDepartementBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriquePrevision result = service.executePrevision(
                GeographicalScope.DEPARTEMENT, "D01", nature, startDate, endDate);
        
        assertSame(expected, result);
    }
    
    // ---- executeAirQuality ----
    @Test
    void testExecuteAirQuality_CommuneScope() {
        AirPolluant polluant = AirPolluant.NO2;
        HistoriqueAirQuality expected = new HistoriqueAirQuality();
        when(airService.getAllByPolluantAndCodeInseeBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriqueAirQuality result = service.executeAirQuality(
                GeographicalScope.COMMUNE, "C001", polluant, startDate, endDate);
        
        assertSame(expected, result);
        verify(validator).validateParams(polluant, "C001", startDate, endDate);
    }
    
    @Test
    void testExecuteAirQuality_RegionScope() {
        AirPolluant polluant = AirPolluant.O3;
        HistoriqueAirQuality expected = new HistoriqueAirQuality();
        when(airService.getAllByPolluantAndCodeRegionBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriqueAirQuality result = service.executeAirQuality(
                GeographicalScope.REGION, "R9", polluant, startDate, endDate);
        
        assertSame(expected, result);
    }
    
    @Test
    void testExecuteAirQuality_DepartementScope() {
        AirPolluant polluant = AirPolluant.SO2;
        HistoriqueAirQuality expected = new HistoriqueAirQuality();
        when(airService.getAllByPolluantAndCodeDepartementBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriqueAirQuality result = service.executeAirQuality(
                GeographicalScope.DEPARTEMENT, "D22", polluant, startDate, endDate);
        
        assertSame(expected, result);
    }
    
    // ---- executePopulation ----
    @Test
    void testExecutePopulation_CommuneScope() {
        HistoriquePopulation expected = new HistoriquePopulation();
        when(popService.getAllByCodeInseeBetwenDates(any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriquePopulation result = service.executePopulation(
                GeographicalScope.COMMUNE, "C123", startDate, endDate);
        
        assertSame(expected, result);
        verify(validator).validateParams("C123", startDate, endDate);
    }
    
    @Test
    void testExecutePopulation_RegionScope() {
        HistoriquePopulation expected = new HistoriquePopulation();
        when(popService.getAllByCodeRegionBetweenDates(any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriquePopulation result = service.executePopulation(
                GeographicalScope.REGION, "R44", startDate, endDate);
        
        assertSame(expected, result);
    }
    
    @Test
    void testExecutePopulation_DepartementScope() {
        HistoriquePopulation expected = new HistoriquePopulation();
        when(popService.getAllByCodeDepartementBetweenDates(any(), any(), any(), any()))
                .thenReturn(expected);
        
        HistoriquePopulation result = service.executePopulation(
                GeographicalScope.DEPARTEMENT, "D66", startDate, endDate);
        
        assertSame(expected, result);
    }
    
    // ---- CSV exports ----
    @Test
    void testExecutePrevisionCsv_callsCsvService() throws IOException, ExportException {
        HistoriquePrevision historique = new HistoriquePrevision();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(prevService.getAllByNatureAndCodeInseeBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(historique);
        
        service.executePrevisionCsv(response, GeographicalScope.COMMUNE, "C1", NatureMesurePrevision.PRESSURE, startDate, endDate);
        
        verify(csvService).buildCsv(response, historique);
    }
    
    @Test
    void testExecuteAirQualityCsv_callsCsvService() throws IOException, ExportException {
        HistoriqueAirQuality historique = new HistoriqueAirQuality();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(airService.getAllByPolluantAndCodeInseeBetweenDates(any(), any(), any(), any(), any()))
                .thenReturn(historique);
        
        service.executeAirQualityCsv(response, GeographicalScope.COMMUNE, "C2", AirPolluant.NO2, startDate, endDate);
        
        verify(csvService).buildCsv(response, historique);
    }
    
    @Test
    void testExecutePopulationCsv_callsCsvService() throws IOException, ExportException {
        HistoriquePopulation historique = new HistoriquePopulation();
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(popService.getAllByCodeInseeBetwenDates(any(), any(), any(), any()))
                .thenReturn(historique);
        
        service.executePopulationCsv(response, GeographicalScope.COMMUNE, "C3", startDate, endDate);
        
        verify(csvService).buildCsv(response, historique);
    }
}