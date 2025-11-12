package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.repository.CommuneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CacheServiceImplTest {
    
    @Mock private CommuneRepository communeRepository;
    
    @InjectMocks private CacheServiceImpl service;
    
    private Commune mockCommune(String codeInsee, int regionCode, String deptCode) {
        Region region = new Region();
        region.setCode(regionCode);
        
        Departement dep = new Departement();
        dep.setCode(deptCode);
        dep.setRegion(region);
        
        Coordonnee coord = new Coordonnee();
        coord.setLatitude(45.0);
        coord.setLongitude(4.0);
        
        Commune commune = new Commune();
        commune.setCodeInsee(codeInsee);
        commune.setDepartement(dep);
        commune.setCoordonnee(coord);
        return commune;
    }
    
    @BeforeEach
    void setup() {
        service.clearCaches();
    }
    
    @Test
    void testLoadExistingCommunesWithRelations() {
        Commune commune = mockCommune("12345", 1, "01");
        when(communeRepository.findAllWithRelations()).thenReturn(List.of(commune));
        
        service.loadExistingCommunesWithRelations();
        
        // verify caching behavior
        assertEquals(commune, service.findInCommuneCache("12345"));
        assertEquals(commune.getDepartement(), service.findInDepartementCache("01"));
        assertEquals(commune.getDepartement().getRegion(), service.findInRegionCache(1));
        assertEquals(commune.getCoordonnee(), service.findInCoordoneeCache("12345"));
        
        verify(communeRepository).findAllWithRelations();
    }
    
    @Test
    void testPutAndFindInCaches() {
        Commune commune = new Commune();
        Region region = new Region();
        region.setCode(42);
        Departement dep = new Departement();
        dep.setCode("69");
        Coordonnee coord = new Coordonnee();
        
        service.putInCommuneCache("c1", commune);
        service.putInRegionCache(42, region);
        service.putInDepartementCache("69", dep);
        service.putInCoordonneeCache("c1", coord);
        
        assertEquals(commune, service.findInCommuneCache("c1"));
        assertEquals(region, service.findInRegionCache(42));
        assertEquals(dep, service.findInDepartementCache("69"));
        assertEquals(coord, service.findInCoordoneeCache("c1"));
    }
    
    @Test
    void testClearCaches() {
        service.putInCommuneCache("x", new Commune());
        service.putInRegionCache(10, new Region());
        service.putInDepartementCache("10", new Departement());
        service.putInCoordonneeCache("x", new Coordonnee());
        
        service.clearCaches();
        
        assertNull(service.findInCommuneCache("x"));
        assertNull(service.findInRegionCache(10));
        assertNull(service.findInDepartementCache("10"));
        assertNull(service.findInCoordoneeCache("x"));
    }
}