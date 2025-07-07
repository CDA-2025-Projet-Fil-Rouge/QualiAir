package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleManagementServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private RoleManagementService service;

    private Utilisateur admin;
    private Utilisateur utilisateur;

    private final String messageDesactive = "Utilisateur désactivé";
    private final String messageReactive = "Utilisateur réactivé";
    private final String messageBanni = "Utilisateur banni";
    private final String messageDebanni = "Utilisateur débanni";

    @BeforeEach
    void setup() {
        admin = new Utilisateur();
        admin.setRole(RoleUtilisateur.ADMIN);

        utilisateur = new Utilisateur();
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);
    }

    @Test
    void toggleUserRole_shouldSwitchToInactif() throws Exception {
        try (MockedStatic<UtilisateurUtils> utils = mockStatic(UtilisateurUtils.class)) {
            utils.when(() -> UtilisateurUtils.isAdmin(admin)).thenReturn(true);
            utils.when(() -> UtilisateurUtils.findUserOrThrow(utilisateurRepository, 2L)).thenReturn(utilisateur);
            utils.when(() -> UtilisateurUtils.computeNextRole(RoleUtilisateur.UTILISATEUR, RoleUtilisateur.INACTIF, RoleUtilisateur.UTILISATEUR))
                    .thenReturn(RoleUtilisateur.INACTIF);
            utils.when(() -> UtilisateurUtils.messageForRoleChange(RoleUtilisateur.INACTIF, RoleUtilisateur.INACTIF, "Utilisateur désactivé", "Utilisateur réactivé"))
                    .thenReturn(messageDesactive);

            String result = service.toggleUserRole(
                    admin,
                    2L,
                    RoleUtilisateur.INACTIF,
                    RoleUtilisateur.UTILISATEUR,
                    messageDesactive,
                    messageReactive
            );

            assertEquals(messageDesactive, result);
            assertEquals(RoleUtilisateur.INACTIF, utilisateur.getRole());
            verify(utilisateurRepository).save(utilisateur);
        }
    }

    @Test
    void toggleUserRole_shouldSwitchBackFromInactifToUtilisateur() throws Exception {
        utilisateur.setRole(RoleUtilisateur.INACTIF);

        try (MockedStatic<UtilisateurUtils> utils = mockStatic(UtilisateurUtils.class)) {
            utils.when(() -> UtilisateurUtils.isAdmin(admin)).thenReturn(true);
            utils.when(() -> UtilisateurUtils.findUserOrThrow(utilisateurRepository, 2L)).thenReturn(utilisateur);
            utils.when(() -> UtilisateurUtils.computeNextRole(RoleUtilisateur.INACTIF, RoleUtilisateur.INACTIF, RoleUtilisateur.UTILISATEUR))
                    .thenReturn(RoleUtilisateur.UTILISATEUR);
            utils.when(() -> UtilisateurUtils.messageForRoleChange(RoleUtilisateur.UTILISATEUR, RoleUtilisateur.INACTIF, "Utilisateur désactivé", "Utilisateur réactivé"))
                    .thenReturn(messageReactive);

            String result = service.toggleUserRole(
                    admin,
                    2L,
                    RoleUtilisateur.INACTIF,
                    RoleUtilisateur.UTILISATEUR,
                    messageDesactive,
                    messageReactive
            );

            assertEquals(messageReactive, result);
            assertEquals(RoleUtilisateur.UTILISATEUR, utilisateur.getRole());
            verify(utilisateurRepository).save(utilisateur);
        }
    }

    @Test
    void toggleUserRole_shouldSwitchToBanni() throws Exception {
        try (MockedStatic<UtilisateurUtils> utils = mockStatic(UtilisateurUtils.class)) {
            utils.when(() -> UtilisateurUtils.isAdmin(admin)).thenReturn(true);
            utils.when(() -> UtilisateurUtils.findUserOrThrow(utilisateurRepository, 2L)).thenReturn(utilisateur);
            utils.when(() -> UtilisateurUtils.computeNextRole(RoleUtilisateur.UTILISATEUR, RoleUtilisateur.BANNI, RoleUtilisateur.UTILISATEUR))
                    .thenReturn(RoleUtilisateur.BANNI);
            utils.when(() -> UtilisateurUtils.messageForRoleChange(RoleUtilisateur.BANNI, RoleUtilisateur.BANNI, messageBanni, messageDebanni))
                    .thenReturn(messageBanni);

            String result = service.toggleUserRole(
                    admin,
                    2L,
                    RoleUtilisateur.BANNI,
                    RoleUtilisateur.UTILISATEUR,
                    messageBanni,
                    messageDebanni
            );

            assertEquals(messageBanni, result);
            assertEquals(RoleUtilisateur.BANNI, utilisateur.getRole());
            verify(utilisateurRepository).save(utilisateur);
        }
    }

    @Test
    void toggleUserRole_shouldSwitchBackFromBanniToUtilisateur() throws Exception {
        utilisateur.setRole(RoleUtilisateur.BANNI);

        try (MockedStatic<UtilisateurUtils> utils = mockStatic(UtilisateurUtils.class)) {
            utils.when(() -> UtilisateurUtils.isAdmin(admin)).thenReturn(true);
            utils.when(() -> UtilisateurUtils.findUserOrThrow(utilisateurRepository, 2L)).thenReturn(utilisateur);
            utils.when(() -> UtilisateurUtils.computeNextRole(RoleUtilisateur.BANNI, RoleUtilisateur.BANNI, RoleUtilisateur.UTILISATEUR))
                    .thenReturn(RoleUtilisateur.UTILISATEUR);
            utils.when(() -> UtilisateurUtils.messageForRoleChange(RoleUtilisateur.UTILISATEUR, RoleUtilisateur.BANNI, messageBanni, messageDebanni))
                    .thenReturn(messageDebanni);

            String result = service.toggleUserRole(
                    admin,
                    2L,
                    RoleUtilisateur.BANNI,
                    RoleUtilisateur.UTILISATEUR,
                    messageBanni,
                    messageDebanni
            );

            assertEquals(messageDebanni, result);
            assertEquals(RoleUtilisateur.UTILISATEUR, utilisateur.getRole());
            verify(utilisateurRepository).save(utilisateur);
        }
    }

    @Test
    void toggleUserRole_shouldThrow_whenRequesterNotAdmin() {
        Utilisateur demandeur = new Utilisateur();
        demandeur.setRole(RoleUtilisateur.UTILISATEUR);

        assertThrows(AccessDeniedException.class, () -> service.toggleUserRole(
                demandeur,
                2L,
                RoleUtilisateur.INACTIF,
                RoleUtilisateur.UTILISATEUR,
                messageDesactive,
                messageReactive
        ));

        verifyNoInteractions(utilisateurRepository);
    }

    @Test
    void toggleUserRole_shouldThrow_whenTargetUserNotFound() {
        when(utilisateurRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> service.toggleUserRole(
                admin,
                2L,
                RoleUtilisateur.INACTIF,
                RoleUtilisateur.UTILISATEUR,
                messageDesactive,
                messageReactive
        ));
    }

    @Test
    void toggleUserRole_shouldThrow_whenTargetUserIsAdmin() {
        Utilisateur cible = new Utilisateur();
        cible.setRole(RoleUtilisateur.ADMIN);
        when(utilisateurRepository.findById(2L)).thenReturn(Optional.of(cible));

        assertThrows(BusinessRuleException.class, () -> service.toggleUserRole(
                admin,
                2L,
                RoleUtilisateur.BANNI,
                RoleUtilisateur.UTILISATEUR,
                messageBanni,
                messageDebanni
        ));
    }
}
