package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.DepartementDao;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.repository.DepartementRepository;
import fr.diginamic.qualiair.validator.IDepartementValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceImplTest {
    
    @Mock private CacheService cacheService;
    @Mock private DepartementRepository departementRepository;
    @Mock private IDepartementValidator departementValidator;
    @Mock private DepartementDao departementDao;
    
    @InjectMocks private DepartementServiceImpl service;
    
    @Test
    void testFindOrCreate_returnsFromCache() {
        Departement dep = new Departement();
        dep.setCode("75");
        
        when(cacheService.findInDepartementCache("75")).thenReturn(dep);
        
        Departement result = service.findOrCreate(dep);
        
        assertSame(dep, result);
        verifyNoInteractions(departementValidator, departementDao);
    }
    
    @Test
    void testFindOrCreate_createsNew() {
        Departement dep = new Departement();
        dep.setCode("34");
        
        when(cacheService.findInDepartementCache("34")).thenReturn(null);
        when(departementDao.save(dep)).thenReturn(dep);
        
        Departement result = service.findOrCreate(dep);
        
        assertNotNull(result);
        verify(departementValidator).validate(dep);
        verify(cacheService).putInDepartementCache("34", dep);
        verify(departementDao).save(dep);
    }
}