package fr.diginamic.qualiair.service.forum;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.forumMapper.MessageMapper;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.service.forumService.MessageServiceImpl;
import fr.diginamic.qualiair.service.forumService.ReactionMessageService;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.MessageValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private MessageValidator messageValidator;
    @Mock
    private ReactionMessageService reactionService;

    private MockedStatic<ForumUtils> forumUtilsMock;
    private MockedStatic<CheckUtils> checkUtilsMock;
    private MockedStatic<UtilisateurUtils> userUtilsMock;

    private Utilisateur utilisateur;
    private Utilisateur utilisateur2;
    private Utilisateur admin;
    private Topic topic;
    private Message message;
    private MessageDto messageDto;

    @BeforeEach
    void setUp() {
        forumUtilsMock = mockStatic(ForumUtils.class);
        checkUtilsMock = mockStatic(CheckUtils.class);
        userUtilsMock  = mockStatic(UtilisateurUtils.class);

        utilisateur = new Utilisateur();
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);
        ReflectionTestUtils.setField(utilisateur, "id", 1L);

        admin = new Utilisateur();
        admin.setRole(RoleUtilisateur.ADMIN);
        ReflectionTestUtils.setField(admin, "id", 2L);

        utilisateur2 = new Utilisateur();
        utilisateur2.setRole(RoleUtilisateur.UTILISATEUR);
        ReflectionTestUtils.setField(utilisateur2, "id", 3L);

        topic = new Topic();
        ReflectionTestUtils.setField(topic, "id", 100L);

        message = new Message();
        ReflectionTestUtils.setField(message, "id", 10L);
        message.setCreateur(utilisateur);
        message.setTopic(topic);
        message.setContenu("Old content");
        message.setNbLike(5);
        message.setNbDislike(2);
        message.setNbSignalement(1);

        messageDto = new MessageDto();
        messageDto.setId(10L);
        messageDto.setIdTopic(100L);
        messageDto.setContenu("New content");
    }

    @AfterEach
    void tearDown() {
        forumUtilsMock.close();
        checkUtilsMock.close();
        userUtilsMock.close();
    }

    @Test
    void getAllMessages_shouldReturnPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(messageRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(message)));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        Page<MessageDto> result = messageService.getAllMessages(pageable);

        assertEquals(1, result.getContent().size());
        verify(messageRepository).findAll(pageable);
        verify(messageMapper).toDto(message);
    }

    @Test
    void getMessagesByTopic_shouldReturnList() {
        Message message2 = new Message();
        ReflectionTestUtils.setField(message2, "id", 11L);
        message2.setTopic(topic);

        when(messageRepository.findByTopicId(100L)).thenReturn(List.of(message, message2));
        // bonne surcharge: toDto(Message, Utilisateur)
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);
        MessageDto dto2 = new MessageDto();
        when(messageMapper.toDto(message2, utilisateur)).thenReturn(dto2);

        List<MessageDto> result = messageService.getMessagesByTopic(100L, utilisateur);

        assertEquals(2, result.size());
        verify(messageRepository).findByTopicId(100L);
    }

    @Test
    void createMessage_user_shouldCreateNewMessage() throws Exception {
        // ForumUtils.findTopicOrThrow est appelé par le service
        forumUtilsMock.when(() -> ForumUtils.findTopicOrThrow(eq(topicRepository), eq(100L)))
                .thenReturn(topic);

        when(messageMapper.toEntity(messageDto)).thenReturn(message);

        MessageDto expectedDto = new MessageDto();
        when(messageMapper.toDto(any(Message.class))).thenReturn(expectedDto);

        MessageDto result = messageService.createMessage(messageDto, utilisateur);

        assertNotNull(result);
        verify(messageValidator).validate(message);
        verify(messageRepository).save(message);
    }

    @Test
    void createMessage_shouldThrow_whenTopicNotFound() {
        forumUtilsMock.when(() -> ForumUtils.findTopicOrThrow(eq(topicRepository), eq(100L)))
                .thenThrow(new FileNotFoundException("Topic introuvable"));

        assertThrows(FileNotFoundException.class, () ->
                messageService.createMessage(messageDto, utilisateur)
        );
    }

    @Test
    void updateMessage_author_shouldUpdateContent() throws Exception {
        // IDs cohérents
        checkUtilsMock.when(() -> CheckUtils.ensureMatchingIds(10L, 10L)).thenAnswer(inv -> null);
        // message + author ok
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        // auteur ou admin -> OK
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(utilisateur), eq(1L)))
                .thenAnswer(inv -> null);
        // topic cible ok
        forumUtilsMock.when(() -> ForumUtils.findTopicOrThrow(eq(topicRepository), eq(100L)))
                .thenReturn(topic);

        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.updateMessage(10L, messageDto, utilisateur);

        assertEquals("New content", result.getContenu());
        verify(messageValidator).validate(message);
        verify(messageRepository).save(message);
    }

    @Test
    void updateMessage_admin_shouldUpdateContent() throws Exception {
        checkUtilsMock.when(() -> CheckUtils.ensureMatchingIds(10L, 10L)).thenAnswer(inv -> null);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(admin), eq(1L)))
                .thenAnswer(inv -> null);
        forumUtilsMock.when(() -> ForumUtils.findTopicOrThrow(eq(topicRepository), eq(100L)))
                .thenReturn(topic);

        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.updateMessage(10L, messageDto, admin);

        assertEquals("New content", result.getContenu());
        verify(messageValidator).validate(message);
        verify(messageRepository).save(message);
    }

    @Test
    void updateMessage_userNonAuthor_shouldThrow() {
        checkUtilsMock.when(() -> CheckUtils.ensureMatchingIds(10L, 10L)).thenAnswer(inv -> null);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        // on force l'interdiction
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(utilisateur2), eq(1L)))
                .thenThrow(new AccessDeniedException("forbidden"));

        assertThrows(AccessDeniedException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur2)
        );
    }

    @Test
    void updateMessage_shouldThrow_whenMessageNotFound() {
        checkUtilsMock.when(() -> CheckUtils.ensureMatchingIds(10L, 10L)).thenAnswer(inv -> null);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenThrow(new FileNotFoundException("Message introuvable"));

        assertThrows(FileNotFoundException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur)
        );
    }

    @Test
    void updateMessage_shouldThrow_whenTopicNotFound() {
        checkUtilsMock.when(() -> CheckUtils.ensureMatchingIds(10L, 10L)).thenAnswer(inv -> null);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(utilisateur), eq(1L)))
                .thenAnswer(inv -> null);
        forumUtilsMock.when(() -> ForumUtils.findTopicOrThrow(eq(topicRepository), eq(100L)))
                .thenThrow(new FileNotFoundException("Topic introuvable"));

        assertThrows(FileNotFoundException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur)
        );
    }

    @Test
    void updateMessage_shouldThrow_whenIdsDoNotMatch() {
        messageDto.setId(99L);
        // on simule la règle d'ID non concordants
        checkUtilsMock.when(() -> CheckUtils.ensureMatchingIds(10L, 99L))
                .thenThrow(new IllegalArgumentException("IDs mismatch"));

        assertThrows(IllegalArgumentException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur)
        );
    }

    @Test
    void deleteMessage_author_shouldRemoveMessage() throws FileNotFoundException {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(utilisateur), eq(1L)))
                .thenAnswer(inv -> null);

        messageService.deleteMessage(10L, utilisateur);
        verify(messageRepository).delete(message);
    }

    @Test
    void deleteMessage_admin_shouldRemoveMessage() throws FileNotFoundException {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(admin), eq(1L)))
                .thenAnswer(inv -> null);

        messageService.deleteMessage(10L, admin);
        verify(messageRepository).delete(message);
    }

    @Test
    void deleteMessage_shouldThrow_whenUserIsNotAdminNorAuthor() {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);
        userUtilsMock.when(() -> UtilisateurUtils.checkAuthorOrAdmin(eq(utilisateur2), eq(1L)))
                .thenThrow(new AccessDeniedException("forbidden"));

        assertThrows(AccessDeniedException.class, () ->
                messageService.deleteMessage(10L, utilisateur2));
    }

    @Test
    void deleteMessage_shouldThrow_whenMessageNotFound() {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenThrow(new FileNotFoundException("Message introuvable"));

        assertThrows(FileNotFoundException.class, () ->
                messageService.deleteMessage(10L, utilisateur)
        );
    }

    @Test
    void reactToMessage_like_shouldAddReaction() throws Exception {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).createReaction(utilisateur, message, ReactionType.LIKE);
        when(messageRepository.save(message)).thenReturn(message);
        // bonne surcharge: toDto(Message, Utilisateur)
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.reactToMessage(10L, utilisateur, ReactionType.LIKE);

        assertEquals(6, message.getNbLike());
        verify(reactionService).createReaction(utilisateur, message, ReactionType.LIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void reactToMessage_dislike_shouldAddReaction() throws Exception {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).createReaction(utilisateur, message, ReactionType.DISLIKE);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.reactToMessage(10L, utilisateur, ReactionType.DISLIKE);

        assertEquals(3, message.getNbDislike());
        verify(reactionService).createReaction(utilisateur, message, ReactionType.DISLIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void reactToMessage_report_shouldAddReaction() throws Exception {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).createReaction(utilisateur, message, ReactionType.REPORT);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.reactToMessage(10L, utilisateur, ReactionType.REPORT);

        assertEquals(2, message.getNbSignalement());
        verify(reactionService).createReaction(utilisateur, message, ReactionType.REPORT);
        verify(messageRepository).save(message);
    }

    @Test
    void reactToMessage_shouldThrow_whenMessageNotFound() {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenThrow(new FileNotFoundException("Message introuvable"));

        assertThrows(FileNotFoundException.class, () ->
                messageService.reactToMessage(10L, utilisateur, ReactionType.LIKE)
        );
    }

    @Test
    void removeReaction_like_shouldRemoveReaction() throws Exception {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).removeReaction(utilisateur, message, ReactionType.LIKE);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.LIKE);

        assertEquals(4, message.getNbLike());
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.LIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_dislike_shouldRemoveReaction() throws Exception {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).removeReaction(utilisateur, message, ReactionType.DISLIKE);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.DISLIKE);

        assertEquals(1, message.getNbDislike());
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.DISLIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_report_shouldRemoveReaction() throws Exception {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).removeReaction(utilisateur, message, ReactionType.REPORT);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.REPORT);

        assertEquals(0, message.getNbSignalement());
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.REPORT);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_like_shouldNotGoBelowZero() throws Exception {
        message.setNbLike(0);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).removeReaction(utilisateur, message, ReactionType.LIKE);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.LIKE);

        assertEquals(0, message.getNbLike(), "Le compteur de likes ne doit pas être négatif");
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.LIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_dislike_shouldNotGoBelowZero() throws Exception {
        message.setNbDislike(0);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).removeReaction(utilisateur, message, ReactionType.DISLIKE);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.DISLIKE);

        assertEquals(0, message.getNbDislike(), "Le compteur de dislikes ne doit pas être négatif");
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.DISLIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_report_shouldNotGoBelowZero() throws Exception {
        message.setNbSignalement(0);
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenReturn(message);

        doNothing().when(reactionService).removeReaction(utilisateur, message, ReactionType.REPORT);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message, utilisateur)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.REPORT);

        assertEquals(0, message.getNbSignalement(), "Le compteur de signalements ne doit pas être négatif");
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.REPORT);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_shouldThrow_whenMessageNotFound() {
        forumUtilsMock.when(() -> ForumUtils.findMessageOrThrow(eq(messageRepository), eq(10L)))
                .thenThrow(new FileNotFoundException("Message introuvable"));

        assertThrows(FileNotFoundException.class, () ->
                messageService.removeReaction(10L, utilisateur, ReactionType.LIKE)
        );
    }
}
