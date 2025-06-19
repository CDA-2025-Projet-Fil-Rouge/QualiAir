package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckUtilsTest {

    @Test
    void ensureMatchingIds_shouldNotThrow_whenIdsAreEqual() {
        assertDoesNotThrow(() -> CheckUtils.ensureMatchingIds(1L, 1L));
    }

    @Test
    void ensureMatchingIds_shouldThrow_whenIdsDiffer() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> CheckUtils.ensureMatchingIds(1L, 2L));
        assertEquals("L'identifiant de l'URL et celui du corps ne correspondent pas.", exception.getMessage());
    }

    @Test
    void ensureUniqueEmail_shouldNotThrow_whenEmailNotExists() throws BusinessRuleException {
        UtilisateurRepository repo = mock(UtilisateurRepository.class);
        when(repo.existsByEmail("nouveau@mail.com")).thenReturn(false);

        assertDoesNotThrow(() -> CheckUtils.ensureUniqueEmail(repo, "nouveau@mail.com"));
    }

    @Test
    void ensureUniqueEmail_shouldThrow_whenEmailExists() {
        UtilisateurRepository repo = mock(UtilisateurRepository.class);
        when(repo.existsByEmail("existant@mail.com")).thenReturn(true);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class,
                () -> CheckUtils.ensureUniqueEmail(repo, "existant@mail.com"));
        assertEquals("Cet email est déjà utilisé par un autre utilisateur", exception.getMessage());
    }
}
