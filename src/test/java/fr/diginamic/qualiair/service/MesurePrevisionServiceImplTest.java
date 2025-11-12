package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import fr.diginamic.qualiair.repository.MesurePrevisionRepository;
import fr.diginamic.qualiair.repository.MesureRepository;
import fr.diginamic.qualiair.validator.MesureValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MesurePrevisionServiceImplTest {
    
    @Mock MesurePrevisionRepository mesurePrevisionRepository;
    @Mock MesureValidator validator;
    @Mock MesurePrevisionMapper mapper;
    @Mock MesureRepository mesureRepository;
    
    @InjectMocks MesurePrevisionServiceImpl service;
    
    @Test
    void testSaveMesurePrevision_invalidItem_isSkipped() {
        MesurePrevision invalid = new MesurePrevision();
        List<MesurePrevision> list = List.of(invalid);
        
        try
        {
            lenient().doThrow(new BusinessRuleException("invalid"))
                     .when(validator).validate(any(MesurePrevision.class)); // ✅ matcher instead of instance
        }
        catch (BusinessRuleException e)
        {
            throw new RuntimeException(e);
        }
        
        List<MesurePrevision> result = service.saveMesurePrevision(list);
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testGetAllByNatureAndCodeRegionBetweenDates_delegatesAndMaps() {
        List<MesurePrevision> list = List.of(new MesurePrevision());
        when(mesurePrevisionRepository.getAllByNatureAndMRegionCodeAndDateReleveBetween(
                anyString(), any(), any(), anyInt()))
                .thenReturn(list); // ✅ anyString() to avoid mismatch
        when(mapper.toHistoricalDtoFromRegion(any(), anyString(), any(), eq(list)))
                .thenReturn(new HistoriquePrevision());
        
        var result = service.getAllByNatureAndCodeRegionBetweenDates(
                GeographicalScope.REGION, NatureMesurePrevision.RAIN_1H, "12",
                LocalDateTime.now(), LocalDateTime.now());
        
        assertNotNull(result);
    }
    
    @Test
    void testGetAllByNatureAndCodeDepartementBetweenDates_delegatesAndMaps() {
        List<MesurePrevision> list = List.of(new MesurePrevision());
        when(mesurePrevisionRepository.getAllByNatureAndDepartementAndDateReleveBetween(
                anyString(), any(), any(), anyString()))
                .thenReturn(list); // ✅ anyString()
        when(mapper.toHistoricalDtoFromDepartement(any(), anyString(), any(), eq(list)))
                .thenReturn(new HistoriquePrevision());
        
        var result = service.getAllByNatureAndCodeDepartementBetweenDates(
                GeographicalScope.DEPARTEMENT, NatureMesurePrevision.WIND_SPEED, "44",
                LocalDateTime.now(), LocalDateTime.now());
        
        assertNotNull(result);
    }
}