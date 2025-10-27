package fr.diginamic.qualiair.service.forum;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionMessage;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.repository.ReactionMessageRepository;
import fr.diginamic.qualiair.service.forumService.ReactionMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReactionMessageServiceTest {

    @Mock
    private ReactionMessageRepository reactionRepository;

    @InjectMocks
    private ReactionMessageServiceImpl service;

    private Utilisateur utilisateur;
    private Message message;

    @BeforeEach
    void setup() {
        utilisateur = new Utilisateur();
        ReflectionTestUtils.setField(utilisateur, "id", 1L);
        utilisateur.setEmail("test@example.com");

        message = new Message();
        ReflectionTestUtils.setField(message, "id", 10L);
        message.setContenu("Ceci est un message test");
    }

    @Test
    void createReaction_shouldSaveNewReaction() throws BusinessRuleException {
        when(reactionRepository.existsByUtilisateurAndMessageAndType(utilisateur, message, ReactionType.LIKE))
                .thenReturn(false);

        service.createReaction(utilisateur, message, ReactionType.LIKE);

        ArgumentCaptor<ReactionMessage> captor = ArgumentCaptor.forClass(ReactionMessage.class);
        verify(reactionRepository).save(captor.capture());

        ReactionMessage saved = captor.getValue();
        assertEquals(utilisateur, saved.getUtilisateur());
        assertEquals(message, saved.getMessage());
        assertEquals(ReactionType.LIKE, saved.getType());
        assertNotNull(saved.getDate());
    }

    @Test
    void createReaction_shouldThrow_whenReactionAlreadyExists() {
        when(reactionRepository.existsByUtilisateurAndMessageAndType(utilisateur, message, ReactionType.LIKE))
                .thenReturn(true);

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () ->
                service.createReaction(utilisateur, message, ReactionType.LIKE));

        assertEquals("Vous avez déjà réagi à ce message", exception.getMessage());
        verify(reactionRepository, never()).save(any());
    }

    @Test
    void removeReaction_shouldDeleteExistingReaction() throws BusinessRuleException {
        ReactionMessage existingReaction = new ReactionMessage();
        existingReaction.setUtilisateur(utilisateur);
        existingReaction.setMessage(message);
        existingReaction.setType(ReactionType.REPORT);

        when(reactionRepository.findByUtilisateurAndMessageAndType(utilisateur, message, ReactionType.REPORT))
                .thenReturn(Optional.of(existingReaction));

        service.removeReaction(utilisateur, message, ReactionType.REPORT);

        verify(reactionRepository).delete(existingReaction);
    }

    @Test
    void removeReaction_shouldThrow_whenReactionNotFound() {
        when(reactionRepository.findByUtilisateurAndMessageAndType(utilisateur, message, ReactionType.DISLIKE))
                .thenReturn(Optional.empty());

        BusinessRuleException exception = assertThrows(BusinessRuleException.class, () ->
                service.removeReaction(utilisateur, message, ReactionType.DISLIKE));

        assertEquals("Aucune réaction trouvée à retirer", exception.getMessage());
        verify(reactionRepository, never()).delete(any());
    }
}
