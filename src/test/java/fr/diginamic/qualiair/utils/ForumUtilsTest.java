package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForumUtilsTest {

    @Mock
    private RubriqueRepository rubriqueRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private MessageRepository messageRepository;

    @Test
    void shouldReturnRubrique_whenIdExists() throws Exception {
        Rubrique rubrique = new Rubrique();
        when(rubriqueRepository.findById(1L)).thenReturn(Optional.of(rubrique));

        Rubrique result = ForumUtils.findRubriqueOrThrow(rubriqueRepository, 1L);
        assertEquals(rubrique, result);
    }

    @Test
    void shouldThrow_whenRubriqueNotFound() {
        when(rubriqueRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FileNotFoundException.class,
                () -> ForumUtils.findRubriqueOrThrow(rubriqueRepository, 1L));
    }

    @Test
    void shouldThrow_whenRubriqueNotEmpty() {
        when(topicRepository.countByRubriqueId(1L)).thenReturn(3L);
        assertThrows(BusinessRuleException.class,
                () -> ForumUtils.assertRubriqueIsEmpty(topicRepository, 1L));
    }

    @Test
    void shouldPass_whenRubriqueIsEmpty() {
        when(topicRepository.countByRubriqueId(1L)).thenReturn(0L);
        assertDoesNotThrow(() -> ForumUtils.assertRubriqueIsEmpty(topicRepository, 1L));
    }

    @Test
    void shouldReturnTopic_whenIdExists() throws Exception {
        Topic topic = new Topic();
        when(topicRepository.findById(2L)).thenReturn(Optional.of(topic));

        Topic result = ForumUtils.findTopicOrThrow(topicRepository, 2L);
        assertEquals(topic, result);
    }

    @Test
    void shouldThrow_whenTopicNotFound() {
        when(topicRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class,
                () -> ForumUtils.findTopicOrThrow(topicRepository, 2L));
    }

    @Test
    void shouldReturnMessage_whenIdExists() throws Exception {
        Message message = new Message();
        when(messageRepository.findById(3L)).thenReturn(Optional.of(message));

        Message result = ForumUtils.findMessageOrThrow(messageRepository, 3L);
        assertEquals(message, result);
    }

    @Test
    void shouldThrow_whenMessageNotFound() {
        when(messageRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(FileNotFoundException.class,
                () -> ForumUtils.findMessageOrThrow(messageRepository, 3L));
    }

    @Test
    void shouldThrow_whenTopicNotEmpty() {
        when(messageRepository.countByTopicId(4L)).thenReturn(5L);
        assertThrows(BusinessRuleException.class,
                () -> ForumUtils.assertTopicIsEmpty(messageRepository, 4L));
    }

    @Test
    void shouldPass_whenTopicIsEmpty() {
        when(messageRepository.countByTopicId(4L)).thenReturn(0L);
        assertDoesNotThrow(() -> ForumUtils.assertTopicIsEmpty(messageRepository, 4L));
    }
}
