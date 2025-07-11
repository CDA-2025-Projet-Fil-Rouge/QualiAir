package fr.diginamic.qualiair.service.forum;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.forumMapper.TopicMapperImpl;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.service.forumService.TopicServiceImpl;
import fr.diginamic.qualiair.validator.forumValidator.TopicValidator;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicRepository topicRepository;
    @Mock
    private TopicMapperImpl topicMapper;
    @Mock
    private RubriqueRepository rubriqueRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private TopicValidator topicValidator;

    private Utilisateur utilisateur;
    private Utilisateur admin;
    private Rubrique rubrique;
    private Topic topic;
    private Topic topic2;
    private TopicDto topicDto;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        ReflectionTestUtils.setField(utilisateur, "id", 1L);
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);

        admin = new Utilisateur();
        ReflectionTestUtils.setField(admin, "id", 2L);
        admin.setRole(RoleUtilisateur.ADMIN);

        rubrique = new Rubrique();
        ReflectionTestUtils.setField(rubrique, "id", 200L);

        topic = new Topic();
        ReflectionTestUtils.setField(topic, "id", 10L);
        topic.setCreateur(utilisateur);
        topic.setRubrique(rubrique);
        topic.setNom("Ancien topic");

        topic2 = new Topic();
        topic2.setRubrique(rubrique);

        topicDto = new TopicDto();
        topicDto.setId(10L);
        topicDto.setIdRubrique(200L);
        topicDto.setNom("Nouveau topic");
    }

    @Test
    void getAllTopics_shouldReturnPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(topicRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(topic, topic2)));

        Page<TopicDto> result = topicService.getAllTopics(pageable);

        assertEquals(2, result.getContent().size());
        verify(topicRepository).findAll(pageable);
    }

    @Test
    void getTopicsByRubrique_shouldReturnList() {
        when(topicRepository.findByRubriqueId(200L)).thenReturn(List.of(topic, topic2));
        when(topicMapper.toDto(topic)).thenReturn(topicDto);

        List<TopicDto> result = topicService.getTopicsByRubrique(200L);

        assertEquals(2, result.size());
        verify(topicRepository).findByRubriqueId(200L);
    }

    @Test
    void createTopic_shouldCreateNewTopic() throws Exception {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        when(topicMapper.toEntity(topicDto)).thenReturn(topic);

        when(topicMapper.toDto(topic)).thenReturn(topicDto);

        TopicDto result = topicService.createTopic(topicDto, utilisateur);

        assertNotNull(result);
        verify(topicValidator).validate(topic);
        verify(topicRepository).save(topic);
    }

    @Test
    void createTopic_shouldThrow_whenRubriqueNotFound() {
        when(topicMapper.toEntity(topicDto)).thenReturn(topic); // manquait
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> topicService.createTopic(topicDto, utilisateur));
    }

    @Test
    void updateTopic_author_shouldUpdateTopicFields() throws Exception {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        when(topicMapper.toDto(topic)).thenReturn(topicDto);

        TopicDto result = topicService.updateTopic(10L, topicDto, utilisateur);

        assertEquals("Nouveau topic", result.getNom());
        verify(topicValidator).validate(topic);
        verify(topicRepository).save(topic);
    }

    @Test
    void updateTopic_admin_shouldUpdateTopicFields() throws Exception {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        when(topicMapper.toDto(topic)).thenReturn(topicDto);

        TopicDto result = topicService.updateTopic(10L, topicDto, admin);

        assertEquals("Nouveau topic", result.getNom());
        verify(topicValidator).validate(topic);
        verify(topicRepository).save(topic);
    }

    @Test
    void updateTopic_shouldThrow_whenUserIsNotAuthorOrAdmin() {
        Utilisateur autreUser = new Utilisateur();
        ReflectionTestUtils.setField(autreUser, "id", 99L);

        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));

        assertThrows(AccessDeniedException.class, () ->
                topicService.updateTopic(10L, topicDto, autreUser));
    }

    @Test
    void updateTopic_shouldThrow_whenTopicNotFound() {
        when(topicRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> topicService.updateTopic(10L, topicDto, utilisateur));
    }

    @Test
    void updateTopic_shouldThrow_whenRubriqueNotFound() {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> topicService.updateTopic(10L, topicDto, utilisateur));
    }

    @Test
    void updateTopic_shouldThrow_whenIdsDoNotMatch() {
        topicDto.setId(99L);
        assertThrows(IllegalArgumentException.class, () ->
                topicService.updateTopic(10L, topicDto, admin)
        );
    }

    @Test
    void deleteTopic_shouldDeleteTopic() throws Exception {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));
        when(messageRepository.countByTopicId(10L)).thenReturn(0L);

        topicService.deleteTopic(10L, admin);

        verify(topicRepository).delete(topic);
    }

    @Test
    void deleteTopic_shouldThrow_whenTopicNotFound() {
        when(topicRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> topicService.deleteTopic(999L, utilisateur));
    }

    @Test
    void deleteTopic_shouldThrow_whenTopicHasMessages() {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));
        when(messageRepository.countByTopicId(10L)).thenReturn(3L);

        assertThrows(BusinessRuleException.class, () -> topicService.deleteTopic(10L, admin));
    }

    @Test
    void deleteTopic_shouldThrow_whenUserIsNotAdmin() {
        when(topicRepository.findById(10L)).thenReturn(Optional.of(topic));

        assertThrows(AccessDeniedException.class, () -> topicService.deleteTopic(10L, utilisateur));

        verify(topicRepository, never()).delete(any());
    }
}
