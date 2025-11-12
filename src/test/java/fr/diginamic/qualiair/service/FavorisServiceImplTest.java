package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.favoris.CodeInsee;
import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.*;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.security.CusomUserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavorisServiceImplTest
{
    
    @Mock
    private CommuneService communeService;
    @Mock
    private CommuneMapper mapper;
    @Mock
    private UtilisateurService utilisateurService;
    
    @InjectMocks
    private FavorisServiceImpl service;
    
    private Utilisateur user;
    private CusomUserPrincipal principal;
    private Commune commune;
    
    @BeforeEach
    void setup()
    {
        user = new Utilisateur();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFavCommunes(new HashSet<>()); // ✅ FIXED (HashSet instead of ArrayList)
    }
    
}