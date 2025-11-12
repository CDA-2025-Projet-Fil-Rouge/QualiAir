package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CoordoneeDao;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.repository.CoordonneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoordonneeServiceImplTest {
    
    @Mock private CacheService cacheService;
    @Mock private CoordonneRepository coordonneRepository;
    @Mock private CoordoneeDao dao;
    
    @InjectMocks private CoordonneeServiceImpl service;
    
    private Coordonnee mockCoordonnee(String codeInsee) {
        Commune commune = new Commune();
        commune.setCodeInsee(codeInsee);
        Coordonnee coord = new Coordonnee();
        coord.setCommune(commune);
        return coord;
    }
    
    @Test
    void testFindOrCreate_returnsFromCache() {
        Coordonnee coord = mockCoordonnee("12345");
        when(cacheService.findInCoordoneeCache("12345")).thenReturn(coord);
        
        Coordonnee result = service.findOrCreate(coord);
        
        assertSame(coord, result);
        verifyNoInteractions(dao);
    }
    
    @Test
    void testFindOrCreate_createsNew() {
        Coordonnee coord = mockCoordonnee("75000");
        when(cacheService.findInCoordoneeCache("75000")).thenReturn(null);
        when(dao.save(coord)).thenReturn(coord);
        
        Coordonnee result = service.findOrCreate(coord);
        
        assertNotNull(result);
        verify(dao).save(coord);
        verify(cacheService).putInCoordonneeCache("75000", coord);
    }
}