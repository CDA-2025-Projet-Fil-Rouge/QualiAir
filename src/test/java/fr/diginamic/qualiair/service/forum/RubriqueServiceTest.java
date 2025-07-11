package fr.diginamic.qualiair.service.forum;


import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.forumMapper.RubriqueMapperImpl;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.service.forumService.RubriqueServiceImpl;
import fr.diginamic.qualiair.validator.forumValidator.RubriqueValidator;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RubriqueServiceTest {

    @InjectMocks
    private RubriqueServiceImpl rubriqueService;

    @Mock
    private RubriqueRepository rubriqueRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private RubriqueMapperImpl rubriqueMapper;
    @Mock
    private RubriqueValidator rubriqueValidator;

    private Utilisateur utilisateur;
    private Utilisateur admin;
    private Utilisateur superadmin;
    private Rubrique rubrique;
    private Rubrique rubrique2;
    private RubriqueDto rubriqueDto;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        ReflectionTestUtils.setField(utilisateur, "id", 1L);
        utilisateur.setRole(RoleUtilisateur.UTILISATEUR);

        admin = new Utilisateur();
        ReflectionTestUtils.setField(admin, "id", 2L);
        admin.setRole(RoleUtilisateur.ADMIN);

        superadmin = new Utilisateur();
        ReflectionTestUtils.setField(superadmin, "id", 3L);
        superadmin.setRole(RoleUtilisateur.SUPERADMIN);

        rubrique = new Rubrique();
        ReflectionTestUtils.setField(rubrique, "id", 200L);
        rubrique.setCreateur(admin);
        rubrique.setNom("Ancien nom");
        rubrique.setDescription("Ancienne description");

        rubrique2 = new Rubrique();

        rubriqueDto = new RubriqueDto();
        rubriqueDto.setId(200L);
        rubriqueDto.setNom("Nouveau nom");
        rubriqueDto.setDescription("Nouvelle description");
    }

    @Test
    void getAllRubriques_shouldReturnPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 5);
        when(rubriqueRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(rubrique, rubrique2)));

        Page<RubriqueDto> result = rubriqueService.getAllRubriques(pageable);

        assertEquals(2, result.getContent().size());
        verify(rubriqueRepository).findAll(pageable);
    }

    @Test
    void createRubrique_admin_shouldCreateNewRubrique() throws Exception {
        when(rubriqueMapper.toEntity(rubriqueDto)).thenReturn(rubrique);

        when(rubriqueMapper.toDto(rubrique)).thenReturn(rubriqueDto);

        RubriqueDto result = rubriqueService.createRubrique(rubriqueDto, admin);

        assertNotNull(result);
        verify(rubriqueValidator).validate(rubrique);
        verify(rubriqueRepository).save(rubrique);
    }

    @Test
    void createRubrique_superadmin_shouldCreateNewRubrique() throws Exception {
        when(rubriqueMapper.toEntity(rubriqueDto)).thenReturn(rubrique);

        when(rubriqueMapper.toDto(rubrique)).thenReturn(rubriqueDto);

        RubriqueDto result = rubriqueService.createRubrique(rubriqueDto, superadmin);

        assertNotNull(result);
        verify(rubriqueValidator).validate(rubrique);
        verify(rubriqueRepository).save(rubrique);
    }

    @Test
    void createRubrique_user_shouldThrow() {
        assertThrows(AccessDeniedException.class, () ->
                rubriqueService.createRubrique(rubriqueDto, utilisateur)
        );
    }

    @Test
    void updateRubrique_admin_shouldUpdateSuccessfully() throws Exception {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        when(rubriqueMapper.toDto(rubrique)).thenReturn(rubriqueDto);

        RubriqueDto result = rubriqueService.updateRubrique(200L, rubriqueDto, admin);

        assertEquals("Nouveau nom", result.getNom());
        assertEquals("Nouvelle description", result.getDescription());
        verify(rubriqueValidator).validate(rubrique);
        verify(rubriqueRepository).save(rubrique);
    }

    @Test
    void updateRubrique_superadmin_shouldUpdateSuccessfully() throws Exception {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        when(rubriqueMapper.toDto(rubrique)).thenReturn(rubriqueDto);

        RubriqueDto result = rubriqueService.updateRubrique(200L, rubriqueDto, superadmin);

        assertEquals("Nouveau nom", result.getNom());
        assertEquals("Nouvelle description", result.getDescription());
        verify(rubriqueValidator).validate(rubrique);
        verify(rubriqueRepository).save(rubrique);
    }

    @Test
    void updateRubrique_user_shouldThrow() {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));

        assertThrows(AccessDeniedException.class, () ->
                rubriqueService.updateRubrique(200L, rubriqueDto, utilisateur)
        );
    }

    @Test
    void updateRubrique_shouldThrow_whenIdMismatch() {
        rubriqueDto.setId(999L);
        assertThrows(IllegalArgumentException.class, () ->
                rubriqueService.updateRubrique(200L, rubriqueDto, admin)
        );
    }

    @Test
    void updateRubrique_shouldThrow_whenRubriqueNotFound() {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.empty());
        assertThrows(FileNotFoundException.class, () ->
                rubriqueService.updateRubrique(200L, rubriqueDto, admin)
        );
    }

    @Test
    void deleteRubrique_admin_shouldDeleteSuccessfully() throws Exception {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        rubriqueService.deleteRubrique(200L, admin);
        verify(rubriqueRepository).delete(rubrique);
    }

    @Test
    void deleteRubrique_superadmin_shouldDeleteSuccessfully() throws Exception {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        rubriqueService.deleteRubrique(200L, superadmin);
        verify(rubriqueRepository).delete(rubrique);
    }

    @Test
    void deleteRubrique_user_shouldThrow() {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));

        assertThrows(AccessDeniedException.class, () ->
                rubriqueService.deleteRubrique(200L, utilisateur)
        );
    }

    @Test
    void deleteRubrique_shouldThrow_whenRubriqueHasTopics() {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.of(rubrique));
        when(topicRepository.countByRubriqueId(200L)).thenReturn(3L);

        assertThrows(BusinessRuleException.class, () ->
                rubriqueService.deleteRubrique(200L, admin)
        );
    }

    @Test
    void deleteRubrique_shouldThrow_whenRubriqueNotFound() {
        when(rubriqueRepository.findById(200L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () ->
                rubriqueService.deleteRubrique(200L, admin)
        );
    }
}
