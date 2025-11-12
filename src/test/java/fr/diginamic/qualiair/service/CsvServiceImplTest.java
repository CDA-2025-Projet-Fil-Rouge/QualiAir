package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.builder.CsvBuilder;
import fr.diginamic.qualiair.dto.historique.*;
import fr.diginamic.qualiair.enumeration.TypeExport;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvServiceImplTest {
    
    private final CsvServiceImpl service = new CsvServiceImpl();
    
    @Test
    void testBuildCsv_HistoriquePrevision() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HistoriquePrevision historique = new HistoriquePrevision();
        
        try (MockedConstruction<CsvBuilder> mocked = mockConstruction(CsvBuilder.class,
                (builder, context) -> when(builder.toLines(historique)).thenReturn(builder))) {
            
            service.buildCsv(response, historique);
            
            CsvBuilder csvBuilder = mocked.constructed().getFirst();
            verify(csvBuilder).toLines(historique);
            verify(csvBuilder).build();
            
           
           
        }
    }
    
    @Test
    void testBuildCsv_HistoriqueAirQuality() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HistoriqueAirQuality historique = new HistoriqueAirQuality();
        
        try (MockedConstruction<CsvBuilder> mocked = mockConstruction(CsvBuilder.class,
                (builder, context) -> when(builder.toLines(historique)).thenReturn(builder))) {
            
            service.buildCsv(response, historique);
            
            CsvBuilder csvBuilder = mocked.constructed().getFirst();
            verify(csvBuilder).toLines(historique);
            verify(csvBuilder).build();
        }
    }
    
    @Test
    void testBuildCsv_HistoriquePopulation() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HistoriquePopulation historique = new HistoriquePopulation();
        
        try (MockedConstruction<CsvBuilder> mocked = mockConstruction(CsvBuilder.class,
                (builder, context) -> when(builder.toLines(historique)).thenReturn(builder))) {
            
            service.buildCsv(response, historique);
            
            CsvBuilder csvBuilder = mocked.constructed().getFirst();
            verify(csvBuilder).toLines(historique);
            verify(csvBuilder).build();
        }
    }
}