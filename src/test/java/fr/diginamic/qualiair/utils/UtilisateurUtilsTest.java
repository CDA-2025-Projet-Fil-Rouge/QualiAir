package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilisateurUtilsTest {

    @Test
    void isAdmin_shouldReturnTrue_ifAdminOrSuperadmin() {
        Utilisateur admin = new Utilisateur();
        admin.setRole(RoleUtilisateur.ADMIN);

        Utilisateur superadmin = new Utilisateur();
        superadmin.setRole(RoleUtilisateur.SUPERADMIN);

        assertTrue(UtilisateurUtils.isAdmin(admin));
        assertTrue(UtilisateurUtils.isAdmin(superadmin));
    }

    @Test
    void isAdmin_shouldThrow_ifNotAdmin() {
        Utilisateur user = new Utilisateur();
        user.setRole(RoleUtilisateur.UTILISATEUR);
        boolean result = UtilisateurUtils.isAdmin(user);
        assertFalse(result);
    }

    @Test
    void checkAuthorOrAdmin_shouldPass_ifAuthor() {
        Utilisateur user = new Utilisateur();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setRole(RoleUtilisateur.UTILISATEUR);
        assertDoesNotThrow(() -> UtilisateurUtils.checkAuthorOrAdmin(user, 1L));
    }

    @Test
    void checkAuthorOrAdmin_shouldPass_ifAdmin() {
        Utilisateur user = new Utilisateur();
        ReflectionTestUtils.setField(user, "id", 2L);
        user.setRole(RoleUtilisateur.ADMIN);
        assertDoesNotThrow(() -> UtilisateurUtils.checkAuthorOrAdmin(user, 1L));
    }

    @Test
    void checkAuthorOrAdmin_shouldThrow_ifNotAuthorOrAdmin() {
        Utilisateur user = new Utilisateur();
        ReflectionTestUtils.setField(user, "id", 2L);
        user.setRole(RoleUtilisateur.UTILISATEUR);
        assertThrows(AccessDeniedException.class,
                () -> UtilisateurUtils.checkAuthorOrAdmin(user, 1L));
    }

    @Test
    void findAdresseOrThrow_shouldReturnAdresse_whenFound() throws FileNotFoundException {
        AdresseRepository repo = mock(AdresseRepository.class);
        Adresse adresse = new Adresse();
        when(repo.findById(1L)).thenReturn(Optional.of(adresse));
        assertEquals(adresse, UtilisateurUtils.findAdresseOrThrow(repo, 1L));
    }

    @Test
    void findAdresseOrThrow_shouldThrow_whenNotFound() {
        AdresseRepository repo = mock(AdresseRepository.class);
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FileNotFoundException.class, () -> UtilisateurUtils.findAdresseOrThrow(repo, 1L));
    }

    @Test
    void findUserOrThrow_shouldReturnUser_whenFound() throws FileNotFoundException {
        UtilisateurRepository repo = mock(UtilisateurRepository.class);
        Utilisateur user = new Utilisateur();
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        assertEquals(user, UtilisateurUtils.findUserOrThrow(repo, 1L));
    }

    @Test
    void findUserOrThrow_shouldThrow_whenNotFound() {
        UtilisateurRepository repo = mock(UtilisateurRepository.class);
        when(repo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FileNotFoundException.class, () -> UtilisateurUtils.findUserOrThrow(repo, 1L));
    }

    @Test
    void computeNextRole_shouldSwitchRoleCorrectly() {
        assertEquals(RoleUtilisateur.ADMIN, UtilisateurUtils.computeNextRole(RoleUtilisateur.SUPERADMIN, RoleUtilisateur.SUPERADMIN, RoleUtilisateur.ADMIN));
        assertEquals(RoleUtilisateur.SUPERADMIN, UtilisateurUtils.computeNextRole(RoleUtilisateur.ADMIN, RoleUtilisateur.SUPERADMIN, RoleUtilisateur.ADMIN));
    }

    @Test
    void messageForRoleChange_shouldReturnCorrectMessage() {
        String setMsg = "Rôle appliqué";
        String revertMsg = "Rôle retiré";
        assertEquals(setMsg, UtilisateurUtils.messageForRoleChange(RoleUtilisateur.ADMIN, RoleUtilisateur.ADMIN, setMsg, revertMsg));
        assertEquals(revertMsg, UtilisateurUtils.messageForRoleChange(RoleUtilisateur.UTILISATEUR, RoleUtilisateur.ADMIN, setMsg, revertMsg));
    }
}
