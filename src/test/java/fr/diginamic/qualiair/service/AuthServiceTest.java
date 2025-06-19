package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.AdresseMapper;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.security.AuthService;
import fr.diginamic.qualiair.security.IJwtAuthentificationService;
import fr.diginamic.qualiair.validator.AdresseValidator;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UtilisateurRepository utilisateurRepository;
    @Mock
    private AdresseRepository adresseRepository;
    @Mock
    private UtilisateurMapper utilisateurMapper;
    @Mock
    private UtilisateurService utilisateurService;
    @Mock
    private IJwtAuthentificationService IJwtAuthentificationService;
    @Mock
    private BCryptPasswordEncoder bcrypt;
    @Mock
    private UtilisateurValidator utilisateurValidator;
    @Mock
    private CommuneRepository communeRepository;
    @Mock
    private AdresseMapper adresseMapper;
    @Mock
    private AdresseValidator adresseValidator;

    private Utilisateur utilisateur;
    private UtilisateurDto dto;
    private Adresse adresse;
    private AdresseDto adresseDto;
    private Commune commune;

    @BeforeEach
    void setUp() {
        dto = new UtilisateurDto();
        dto.setNom("Test");
        dto.setPrenom("User");
        dto.setEmail("test@example.com");
        dto.setMotDePasse("password");

        utilisateur = new Utilisateur();
        ReflectionTestUtils.setField(utilisateur, "id", 1L);
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);
        utilisateur.setEmail("test@example.com");

        adresse = new Adresse();
        ReflectionTestUtils.setField(adresse, "id", 1L);

        adresseDto = new AdresseDto();
        adresseDto.setNumeroRue("12");
        adresseDto.setLibelleRue("Rue des Lilas");
        adresseDto.setCodePostal("75000");
        adresseDto.setNomCommune("Paris");
        dto.setAdresseDto(adresseDto);

        commune = new Commune();
    }

    @Test
    void logUser_shouldReturnJwtCookie_whenCredentialsAreCorrect() {
        when(utilisateurService.getUser("test@example.com")).thenReturn(utilisateur);
        when(bcrypt.matches("password", utilisateur.getMotDePasse())).thenReturn(true);
        ResponseCookie fakeCookie = ResponseCookie.from("token", "dummyJwt").build();
        when(IJwtAuthentificationService.generateToken(utilisateur)).thenReturn(fakeCookie);

        ResponseCookie result = authService.logUser(dto);

        assertEquals("dummyJwt", result.getValue());
        verify(utilisateurService).getUser("test@example.com");
        verify(IJwtAuthentificationService).generateToken(utilisateur);
    }

    @Test
    void logUser_shouldThrow_whenUserNotFound() {
        when(utilisateurService.getUser("invalide@example.com"))
                .thenThrow(new UsernameNotFoundException("Utilisateur non trouvé"));
        dto.setEmail("invalide@example.com");

        assertThrows(BadCredentialsException.class, () ->
            authService.logUser(dto));
    }

    @Test
    void logUser_shouldThrow_whenPasswordIncorrect() {
        utilisateur.setMotDePasse("hashedPassword");

        when(utilisateurService.getUser("test@example.com")).thenReturn(utilisateur);
        when(bcrypt.matches("password", "hashedPassword")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> authService.logUser(dto));
    }

    @Test
    void createUser_shouldSaveUserSuccessfully() throws Exception {
        when(communeRepository.findByNomReelAndCodePostal("Paris", "75000"))
                .thenReturn(Optional.of(commune));
        when(adresseMapper.fromDto(adresseDto, commune)).thenReturn(adresse);
        when(utilisateurMapper.fromDto(dto, adresse, RoleUtilisateur.UTILISATEUR))
                .thenReturn(utilisateur);

        authService.createUser(dto, RoleUtilisateur.UTILISATEUR);

        assertNotEquals("password", utilisateur.getMotDePasse());
        verify(adresseValidator).validate(adresse);
        verify(adresseRepository).save(adresse);
        verify(utilisateurValidator).validate(utilisateur);
        verify(utilisateurRepository).save(utilisateur);
    }

    @Test
    void createUser_shouldThrow_whenAdresseNotFound() {
          assertThrows(FileNotFoundException.class,
                () -> authService.createUser(dto, RoleUtilisateur.UTILISATEUR));
    }

    @Test
    void createUser_shouldThrow_whenCommuneNotFound() {
        when(communeRepository.findByNomReelAndCodePostal("Paris", "75000"))
                .thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class,
                () -> authService.createUser(dto, RoleUtilisateur.UTILISATEUR));
    }
}
