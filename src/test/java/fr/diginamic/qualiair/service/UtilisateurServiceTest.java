package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.AdresseMapper;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.validator.AdresseValidator;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @InjectMocks
    private UtilisateurService service;

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private CommuneRepository communeRepository;

    @Mock
    private AdresseMapper adresseMapper;

    @Mock
    private UtilisateurMapper utilisateurMapper;

    @Mock
    private UtilisateurValidator utilisateurValidator;

    @Mock
    private AdresseValidator adresseValidator;

    @Mock
    private RoleManagementService roleManagementService;

    @Mock
    private BCryptPasswordEncoder bcrypt;

    private Utilisateur utilisateur;
    private UtilisateurDto utilisateurDto;
    private UtilisateurUpdateDto utilisateurUpdateDto;
    private Utilisateur admin;
    private Utilisateur superadmin;
    private Adresse adresse;
    private AdresseDto adresseDto;
    private Commune commune;

    @BeforeEach
    void setUp() {

        // Création de données communes aux tests
        utilisateur = new Utilisateur();
        ReflectionTestUtils.setField(utilisateur, "id", 1L);
        utilisateur.setEmail("old@example.com");
        utilisateur.setMotDePasse("encodedOldPassword");
        utilisateur.setPrenom("Jean");
        utilisateur.setNom("Dupont");

        admin = new Utilisateur();
        admin.setRole(RoleUtilisateur.ADMIN);

        superadmin = new Utilisateur();
        superadmin.setRole(RoleUtilisateur.SUPERADMIN);

        utilisateurDto = new UtilisateurDto();
        utilisateurDto.setId(1L);
        utilisateurDto.setEmail("old@example.com");

        utilisateurUpdateDto = new UtilisateurUpdateDto();
        utilisateurUpdateDto.setId(1L);
        utilisateurUpdateDto.setEmail("new@example.com");
        utilisateurUpdateDto.setPrenom("Pierre");
        utilisateurUpdateDto.setNom("Martin");
        utilisateurUpdateDto.setAncienMotDePasse("plainOldPassword");
        utilisateurUpdateDto.setNouveauMotDePasse("newPassword");

        adresse = new Adresse();
        ReflectionTestUtils.setField(adresse, "id", 123L);
        utilisateur.setAdresse(adresse);

        adresseDto = new AdresseDto();
        adresseDto.setNumeroRue("10");
        adresseDto.setLibelleRue("Rue des Tests");
        adresseDto.setNomCommune("Testville");
        adresseDto.setCodePostal("12345");
        utilisateurDto.setAdresseDto(adresseDto);
        utilisateurUpdateDto.setAdresseDto(adresseDto);

        commune = new Commune();
    }

    @Test
    void getUser_shouldReturnUser_whenEmailExists() {
        String email = utilisateur.getEmail();
        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.of(utilisateur));

        Utilisateur result = service.getUser(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(utilisateurRepository).findByEmail(email);
    }

    @Test
    void getUser_shouldThrowException_whenEmailNotFound() {
        String email = "unknown@example.com";
        when(utilisateurRepository.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class, () -> service.getUser(email));

        assertTrue(exception.getMessage().contains("Utilisateur non trouvé avec l'email"));
        verify(utilisateurRepository).findByEmail(email);
    }

    @Test
    void getPersonalData_shouldReturnDto_whenUserExists() throws FileNotFoundException {
        Long id = utilisateur.getId();
        when(utilisateurRepository.findById(id)).thenReturn(Optional.of(utilisateur));
        when(utilisateurMapper.toDto(utilisateur)).thenReturn(utilisateurDto);

        UtilisateurDto result = service.getPersonalData(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertNotNull(result.getAdresseDto());
        assertEquals("10", result.getAdresseDto().getNumeroRue());
        assertEquals("Rue des Tests", result.getAdresseDto().getLibelleRue());
        assertEquals("12345", result.getAdresseDto().getCodePostal());
        assertEquals("Testville", result.getAdresseDto().getNomCommune());
        verify(utilisateurRepository).findById(id);
        verify(utilisateurMapper).toDto(utilisateur);
    }

    @Test
    void getPersonalData_shouldThrow_whenUserNotFound() {
        Long id = 99L;
        when(utilisateurRepository.findById(id)).thenReturn(Optional.empty());

        FileNotFoundException ex = assertThrows(FileNotFoundException.class,
                () -> service.getPersonalData(id));

        assertTrue(ex.getMessage().contains("Utilisateur introuvable"));
        verify(utilisateurRepository).findById(id);
    }

    @Test
    void getAllUsers_shouldReturnPage_whenRequesterIsAdmin() {
        Pageable pageable = PageRequest.of(0, 2);
        Utilisateur user2 = new Utilisateur();
        Page<Utilisateur> page = new PageImpl<>(List.of(utilisateur, user2));

        UtilisateurDto dto2 = new UtilisateurDto();

        when(utilisateurRepository.findAll(pageable)).thenReturn(page);
        when(utilisateurMapper.toDto(utilisateur)).thenReturn(utilisateurDto);
        when(utilisateurMapper.toDto(user2)).thenReturn(dto2);

        Page<UtilisateurDto> result = service.getAllUsers(pageable, admin);

        assertEquals(2, result.getContent().size());
        verify(utilisateurRepository).findAll(pageable);
        verify(utilisateurMapper).toDto(utilisateur);
        verify(utilisateurMapper).toDto(user2);
    }

    @Test
    void getAllUsers_shouldThrowAccessDenied_whenRequesterIsNotAdmin() {
        Pageable pageable = PageRequest.of(0, 1);
        assertThrows(AccessDeniedException.class,
                () -> service.getAllUsers(pageable, utilisateur));
    }

    @Test
    void updatePersonalData_shouldUpdateFields() throws BusinessRuleException, FileNotFoundException {
        when(utilisateurMapper.toUpdateDto(utilisateur)).thenReturn(utilisateurUpdateDto);
        when(utilisateurRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(bcrypt.matches("plainOldPassword", "encodedOldPassword")).thenReturn(true);
        when(bcrypt.encode("newPassword")).thenReturn("encodedNewPassword");
        when(communeRepository.findByNomReelAndCodePostalContaining("Testville", "12345"))
                .thenReturn(Optional.of(commune));
        when(adresseMapper.fromDto(adresseDto, commune)).thenReturn(adresse);

        UtilisateurUpdateDto result = service.updatePersonalData(utilisateur, utilisateurUpdateDto);

        assertEquals("new@example.com", result.getEmail());
        assertEquals("Pierre", result.getPrenom());
        assertEquals("Martin", result.getNom());
        assertEquals("encodedNewPassword", utilisateur.getMotDePasse());

        verify(utilisateurRepository).save(utilisateur);
    }

    @Test
    void updatePersonalData_shouldThrowException_whenOldPasswordIncorrect() {
        when(utilisateurRepository.existsByEmail("new@example.com")).thenReturn(false);
        utilisateurUpdateDto.setAncienMotDePasse("wrongPassword");
        when(bcrypt.matches("wrongPassword", "encodedOldPassword")).thenReturn(false);
        when(communeRepository.findByNomReelAndCodePostalContaining("Testville", "12345"))
                .thenReturn(Optional.of(commune));
        when(adresseMapper.fromDto(adresseDto, commune)).thenReturn(adresse);

        assertThrows(BusinessRuleException.class, () ->
                service.updatePersonalData(utilisateur, utilisateurUpdateDto));
    }

    @Test
    void updatePersonalData_shouldThrowException_whenEmailAlreadyUsed() {
        UtilisateurUpdateDto dto = new UtilisateurUpdateDto();
        dto.setId(1L);
        dto.setEmail("used@example.com");

        when(utilisateurRepository.existsByEmail("used@example.com")).thenReturn(true);

        assertThrows(BusinessRuleException.class, () ->
                service.updatePersonalData(utilisateur, dto));
    }

    @Test
    void updatePersonalData_shouldThrowException_whenIdsMismatch() {
        UtilisateurUpdateDto dto = new UtilisateurUpdateDto();
        dto.setId(2L);

        assertThrows(IllegalArgumentException.class, () ->
                service.updatePersonalData(utilisateur, dto));
    }

    @Test
    void toggleAdminUser_shouldReturnExpectedMessage() throws FileNotFoundException, BusinessRuleException {
        String expectedMessage = "Rôle mis à jour : utilisateur devenu admin";

        when(roleManagementService.toggleUserRole(
                admin,
                1L,
                RoleUtilisateur.ADMIN,
                RoleUtilisateur.UTILISATEUR,
                "Rôle mis à jour : utilisateur devenu admin",
                "Rôle mis à jour: admin devenu utilisateur"
        )).thenReturn(expectedMessage);

        String result = service.toggleAdminUser(1L, admin);

        assertEquals(expectedMessage, result);
        verify(roleManagementService).toggleUserRole(
                admin,
                1L,
                RoleUtilisateur.ADMIN,
                RoleUtilisateur.UTILISATEUR,
                "Rôle mis à jour : utilisateur devenu admin",
                "Rôle mis à jour: admin devenu utilisateur"
        );
    }

    @Test
    void toggleActivationUser_shouldReturnExpectedMessage() throws FileNotFoundException, BusinessRuleException {
        String expectedMessage = "Utilisateur désactivé";

        when(roleManagementService.toggleUserRole(
                admin,
                1L,
                RoleUtilisateur.INACTIF,
                RoleUtilisateur.UTILISATEUR,
                "Utilisateur désactivé",
                "Utilisateur réactivé"
        )).thenReturn(expectedMessage);

        String result = service.toggleActivationUser(1L, admin);

        assertEquals(expectedMessage, result);
        verify(roleManagementService).toggleUserRole(
                admin,
                1L,
                RoleUtilisateur.INACTIF,
                RoleUtilisateur.UTILISATEUR,
                "Utilisateur désactivé",
                "Utilisateur réactivé"
        );
    }

    @Test
    void toggleBanUser_shouldReturnUnbanMessage() throws FileNotFoundException, BusinessRuleException {
        String expectedMessage = "Utilisateur débanni";

        when(roleManagementService.toggleUserRole(
                superadmin,
                3L,
                RoleUtilisateur.BANNI,
                RoleUtilisateur.UTILISATEUR,
                "Utilisateur banni",
                "Utilisateur débanni"
        )).thenReturn(expectedMessage);

        String result = service.toggleBanUser(3L, superadmin);

        assertEquals(expectedMessage, result);
        verify(roleManagementService).toggleUserRole(
                superadmin,
                3L,
                RoleUtilisateur.BANNI,
                RoleUtilisateur.UTILISATEUR,
                "Utilisateur banni",
                "Utilisateur débanni"
        );
    }

    @Test
    void toggleBanUser_shouldThrowAccessDenied_whenUserIsNotAdmin() {
        Long idCible = 4L;

        assertThrows(AccessDeniedException.class, () ->
                service.toggleBanUser(idCible, utilisateur));

        verifyNoInteractions(roleManagementService);
    }
}
