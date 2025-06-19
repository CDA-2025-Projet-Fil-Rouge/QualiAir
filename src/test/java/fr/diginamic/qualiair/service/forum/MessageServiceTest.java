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
import fr.diginamic.qualiair.service.forumService.MessageService;
import fr.diginamic.qualiair.service.forumService.ReactionMessageService;
import fr.diginamic.qualiair.validator.forumValidator.MessageValidator;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

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

    private Utilisateur utilisateur;
    private Utilisateur utilisateur2;
    private Utilisateur admin;
    private Topic topic;
    private Message message;
    private MessageDto messageDto;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);
        ReflectionTestUtils.setField(utilisateur, "id", 1L);

        admin = new Utilisateur();
        admin.setRole(RoleUtilisateur.ADMIN);
        ReflectionTestUtils.setField(admin, "id", 2L);

        utilisateur2 = new Utilisateur();
        utilisateur2.setRole(RoleUtilisateur.UTILISATEUR);
        ReflectionTestUtils.setField(utilisateur2, "id", 2L);

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
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        List<MessageDto> result = messageService.getMessagesByTopic(100L);

        assertEquals(2, result.size());
        verify(messageRepository).findByTopicId(100L);
    }

    @Test
    void createMessage_user_shouldCreateNewMessage() throws Exception {
        when(topicRepository.findById(100L)).thenReturn(Optional.of(topic));
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
        when(topicRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                messageService.createMessage(messageDto, utilisateur)
        );
    }

    @Test
    void updateMessage_author_shouldUpdateContent() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(topicRepository.findById(100L)).thenReturn(Optional.of(topic));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.updateMessage(10L, messageDto, utilisateur);

        assertEquals("New content", result.getContenu());
        verify(messageValidator).validate(message);
        verify(messageRepository).save(message);
    }

    @Test
    void updateMessage_admin_shouldUpdateContent() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(topicRepository.findById(100L)).thenReturn(Optional.of(topic));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.updateMessage(10L, messageDto, admin);

        assertEquals("New content", result.getContenu());
        verify(messageValidator).validate(message);
        verify(messageRepository).save(message);
    }

    @Test
    void updateMessage_userNonAuthor_shouldThrow() {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));

        assertThrows(AccessDeniedException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur2)
        );
    }

    @Test
    void updateMessage_shouldThrow_whenMessageNotFound() {
        when(messageRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur)
        );
    }

    @Test
    void updateMessage_shouldThrow_whenTopicNotFound() {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(topicRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur)
        );
    }

    @Test
    void updateMessage_shouldThrow_whenIdsDoNotMatch() {
        messageDto.setId(99L);
        assertThrows(IllegalArgumentException.class, () ->
                messageService.updateMessage(10L, messageDto, utilisateur)
        );
    }

    @Test
    void deleteMessage_author_shouldRemoveMessage() throws FileNotFoundException {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        messageService.deleteMessage(10L, utilisateur);
        verify(messageRepository).delete(message);
    }

    @Test
    void deleteMessage_admin_shouldRemoveMessage() throws FileNotFoundException {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        messageService.deleteMessage(10L, admin);
        verify(messageRepository).delete(message);
    }

    @Test
    void deleteMessage_shouldThrow_whenUserIsNotAdminNorAuthor() {
       when(messageRepository.findById(10L)).thenReturn(Optional.of(message));

        assertThrows(AccessDeniedException.class, () ->
                messageService.deleteMessage(10L, utilisateur2));
    }

    @Test
    void deleteMessage_shouldThrow_whenMessageNotFound() {
        when(messageRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                messageService.deleteMessage(10L, utilisateur)
        );
    }

    @Test
    void reactToMessage_like_shouldAddReaction() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.reactToMessage(10L, utilisateur, ReactionType.LIKE);

        assertEquals(6, message.getNbLike());
        verify(reactionService).createReaction(utilisateur, message, ReactionType.LIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void reactToMessage_dislike_shouldAddReaction() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.reactToMessage(10L, utilisateur, ReactionType.DISLIKE);

        assertEquals(3, message.getNbDislike());
        verify(reactionService).createReaction(utilisateur, message, ReactionType.DISLIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void reactToMessage_report_shouldAddReaction() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.reactToMessage(10L, utilisateur, ReactionType.REPORT);

        assertEquals(2, message.getNbSignalement());
        verify(reactionService).createReaction(utilisateur, message, ReactionType.REPORT);
        verify(messageRepository).save(message);
    }

    @Test
    void reactToMessage_shouldThrow_whenMessageNotFound() {
        when(messageRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                messageService.reactToMessage(10L, utilisateur, ReactionType.LIKE)
        );
    }

    @Test
    void removeReaction_like_shouldRemoveReaction() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.LIKE);

        assertEquals(4, message.getNbLike());
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.LIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_dislike_shouldRemoveReaction() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.DISLIKE);

        assertEquals(1, message.getNbDislike());
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.DISLIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_report_shouldRemoveReaction() throws Exception {
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.REPORT);

        assertEquals(0, message.getNbSignalement());
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.REPORT);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_like_shouldNotGoBelowZero() throws Exception {
        message.setNbLike(0);
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.LIKE);

        assertEquals(0, message.getNbLike(), "Le compteur de likes ne doit pas être négatif");
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.LIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_dislike_shouldNotGoBelowZero() throws Exception {
        message.setNbDislike(0);
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.DISLIKE);

        assertEquals(0, message.getNbDislike(), "Le compteur de dislikes ne doit pas être négatif");
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.DISLIKE);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_report_shouldNotGoBelowZero() throws Exception {
        message.setNbSignalement(0);
        when(messageRepository.findById(10L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        messageService.removeReaction(10L, utilisateur, ReactionType.REPORT);

        assertEquals(0, message.getNbSignalement(), "Le compteur de signalements ne doit pas être négatif");
        verify(reactionService).removeReaction(utilisateur, message, ReactionType.REPORT);
        verify(messageRepository).save(message);
    }

    @Test
    void removeReaction_shouldThrow_whenMessageNotFound() {
        when(messageRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                messageService.removeReaction(10L, utilisateur, ReactionType.LIKE)
        );
    }
}
