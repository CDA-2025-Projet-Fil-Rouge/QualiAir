package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CommuneDao;
import fr.diginamic.qualiair.dto.carte.FiveDaysForecastView;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.*;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.validator.CommuneValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommuneServiceImplTest {
    
    @Mock private CacheService cacheService;
    @Mock private CommuneRepository communeRepository;
    @Mock private CommuneValidator communeValidator;
    @Mock private CommuneDao dao;
    @Mock private CommuneMapper mapper;
    
    @InjectMocks private CommuneServiceImpl service;
    
    // ---- findOrCreate ----
    @Test
    void testFindOrCreate_returnsFromCache() {
        Commune commune = new Commune();
        commune.setCodeInsee("12345"); // ✅ ensure key not null
        when(cacheService.findInCommuneCache("12345")).thenReturn(commune);
        
        Commune result = service.findOrCreate(commune);
        
        assertSame(commune, result);
        verify(cacheService).findInCommuneCache("12345");
        verifyNoInteractions(communeValidator, dao);
    }
    
    @Test
    void testFindOrCreate_createsNew() {
        Commune commune = new Commune();
        commune.setCodeInsee("12345");
        when(cacheService.findInCommuneCache("12345")).thenReturn(null);
        when(dao.save(commune)).thenReturn(commune);
        
        Commune result = service.findOrCreate(commune);
        
        assertNotNull(result);
        verify(communeValidator).validate(commune);
        verify(cacheService).putInCommuneCache("12345", commune);
    }
    
    // ---- getFromCache ----
    @Test
    void testGetFromCache() {
        Commune commune = new Commune();
        when(cacheService.findInCommuneCache("x")).thenReturn(commune);
        
        assertEquals(commune, service.getFromCache("x"));
    }
    
    // ---- getListCommunesDtoByPopulation ----
    @Test
    void testGetListCommunesDtoByPopulation() {
        Commune commune = new Commune();
        when(communeRepository.findCommuneIdsByPopulation(100)).thenReturn(List.of(1L));
        when(communeRepository.findWithMesuresById(List.of(1L))).thenReturn(List.of(commune));
        InfoCarteCommune dto = new InfoCarteCommune();
        when(mapper.toMapDataView(commune)).thenReturn(dto);
        
        List<InfoCarteCommune> result = service.getListCommunesDtoByPopulation(100);
        
        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
    
    // ---- getListTopCommunesByPopulation ----
    @Test
    void testGetListTopCommunesByPopulation() {
        when(communeRepository.findTopByLastestMesurePopulation(200)).thenReturn(List.of(new Commune()));
        
        assertEquals(1, service.getListTopCommunesByPopulation(200).size());
    }
    
    // ---- getAllCommunesWithCoordinates ----
    @Test
    void testGetAllCommunesWithCoordinates() {
        when(communeRepository.findAllWithCoordinates()).thenReturn(List.of(new Commune()));
        assertEquals(1, service.getAllCommunesWithCoordinates().size());
    }
    
    // ---- getAllFavoritesByUserId ----
    @Test
    void testGetAllFavoritesByUserId_valid() {
        when(communeRepository.findAllFavoritesByUserId(1L)).thenReturn(List.of(new Commune()));
        assertEquals(1, service.getAllFavoritesByUserId(1L).size());
    }
    
    @Test
    void testGetAllFavoritesByUserId_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> service.getAllFavoritesByUserId(null));
    }
    
    // ---- getCommuneById ----
    @Test
    void testGetCommuneById_valid() throws Exception {
        Commune c = new Commune();
        when(communeRepository.getCommuneById(1L)).thenReturn(c);
        assertSame(c, service.getCommuneById(1L));
    }
    
    @Test
    void testGetCommuneById_notFound() {
        when(communeRepository.getCommuneById(1L)).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> service.getCommuneById(1L));
    }
    
    @Test
    void testGetCommuneById_nullId() {
        assertThrows(IllegalArgumentException.class, () -> service.getCommuneById(null));
    }
    
    // ---- getCommuneByCodeInsee ----
    @Test
    void testGetCommuneByCodeInsee_valid() throws Exception {
        Commune c = new Commune();
        when(communeRepository.findCommunesByCodeInsee("75056")).thenReturn(c);
        assertSame(c, service.getCommuneByCodeInsee("75056"));
    }
    
    @Test
    void testGetCommuneByCodeInsee_invalid() {
        assertThrows(IllegalArgumentException.class, () -> service.getCommuneByCodeInsee(""));
        assertThrows(IllegalArgumentException.class, () -> service.getCommuneByCodeInsee("TOOLONGCODE"));
    }
    
    @Test
    void testGetCommuneByCodeInsee_notFound() {
        when(communeRepository.findCommunesByCodeInsee("12345")).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> service.getCommuneByCodeInsee("12345"));
    }
    
    // ---- getCommuneDtoByCodeInsee ----
    @Test
    void testGetCommuneDtoByCodeInsee() throws Exception {
        Commune commune = new Commune();
        when(communeRepository.findCommuneIdByCodeInsee("12345")).thenReturn(1L);
        when(communeRepository.findWithMesuresById(1L)).thenReturn(commune);
        InfoCarteCommune dto = new InfoCarteCommune();
        when(mapper.toMapDataView(commune)).thenReturn(dto);
        
        InfoCarteCommune result = service.getCommuneDtoByCodeInsee("12345");
        
        assertSame(dto, result);
    }
    
    // ---- getCommuneForecastByCodeInsee ----
    @Test
    void testGetCommuneForecastByCodeInsee() throws Exception {
        Commune commune = new Commune();
        when(communeRepository.findCommuneIdByCodeInsee("12345")).thenReturn(1L);
        when(communeRepository.findWithForecastMesuresById(anyLong(), any(), any()))
                .thenReturn(commune);
        FiveDaysForecastView view = new FiveDaysForecastView();
        when(mapper.toForecastView(commune)).thenReturn(view);
        
        FiveDaysForecastView result = service.getCommuneForecastByCodeInsee("12345");
        
        assertSame(view, result);
    }
    
    // ---- Error paths ----
    @Test
    void testFindCommuneByCodeInseeWithRelations_missingId() {
        when(communeRepository.findCommuneIdByCodeInsee("x")).thenReturn(null);
        assertThrows(DataNotFoundException.class, () -> service.getCommuneDtoByCodeInsee("x"));
    }
    
    @Test
    void testFindCommuneByCodeInseeWithRelations_nullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> service.getCommuneDtoByCodeInsee(null));
        assertThrows(IllegalArgumentException.class, () -> service.getCommuneDtoByCodeInsee(" "));
    }
    
    // ---- unsupported methods ----
    @Test
    void testUpdateByName_throwsUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> service.updateByName(new Commune()));
    }
    
    @Test
    void testMatchTop10ByName_shortTerm_throws() {
        assertThrows(RouteParamException.class, () -> service.matchTop10ByName("ab"));
    }
    
    @Test
    void testMatchTop10ByName_notSupportedYet() {
        assertThrows(UnsupportedOperationException.class, () -> service.matchTop10ByName("Paris"));
    }
}