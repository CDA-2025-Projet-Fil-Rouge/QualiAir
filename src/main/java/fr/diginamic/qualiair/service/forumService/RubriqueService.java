package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.forumMapper.RubriqueMapper;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.RubriqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service de gestion des rubriques du forum.
 * Gère la récupération, la création et les règles métier associées à la gestion des rubriques.
 * Seule un utilisateur ayant le rôle ADMIN peut créer une nouvelle rubrique.
 */
@Service
public class RubriqueService {

    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Autowired
    private RubriqueMapper rubriqueMapper;
    @Autowired
    private RubriqueValidator rubriqueValidator;
    @Autowired
    private TopicRepository topicRepository;

    /**
     * Récupère toutes les rubriques existantes en base.
     *
     * @return une liste de RubriqueDto représentant les rubriques disponibles.
     */
    public Page<RubriqueDto> getAllRubriques(Pageable pageable) {
        return rubriqueRepository.findAll(pageable).map(rubriqueMapper::toDto);
    }

    /**
     * Crée une nouvelle rubrique, uniquement si l'utilisateur est administrateur.
     *
     * @param dto les informations de la rubrique à créer.
     * @param createur l'utilisateur connecté, devant avoir le rôle ADMIN.
     * @return la rubrique créée sous forme de DTO.
     * @throws AccessDeniedException si l'utilisateur n'est pas ADMIN.
     * @throws BusinessRuleException si la rubrique ne respecte pas les règles de validation métier.
     */
    public RubriqueDto createRubrique(RubriqueDto dto, Utilisateur createur)
            throws BusinessRuleException, TokenExpiredException {
        UtilisateurUtils.isAdmin(createur);

        Rubrique rubrique = rubriqueMapper.toEntity(dto);
        rubrique.setCreateur(createur);
        rubrique.setDateCreation(LocalDateTime.now());
        rubriqueValidator.validate(rubrique);
        rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }

    /**
     * Met à jour une rubrique existante si l'utilisateur est un administrateur.
     *
     * @param idRubrique identifiant de la rubrique à modifier.
     * @param dto les nouvelles données de la rubrique.
     * @param modificateur l'utilisateur connecté tentant la modification.
     * @return la rubrique modifiée sous forme de DTO.
     * @throws AccessDeniedException si l'utilisateur n'est pas admin.
     * @throws IllegalArgumentException si la rubrique est introuvable ou invalide.
     * @throws BusinessRuleException si la rubrique modifiée ne respecte pas les règles métier.
     */
    public RubriqueDto updateRubrique(Long idRubrique, RubriqueDto dto, Utilisateur modificateur)
        throws BusinessRuleException, FileNotFoundException, TokenExpiredException {

        ForumUtils.ensureMatchingIds(idRubrique, dto.getId());
        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, idRubrique);
        UtilisateurUtils.isAdmin(modificateur);

        rubrique.setNom(dto.getNom());
        rubrique.setDateModification(LocalDateTime.now());
        rubrique.setModificateur(modificateur);
        rubriqueValidator.validate(rubrique);
        rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }

    /**
     * Supprime une rubrique existante, à la condition que l'utilisateur soit admin et que la rubrique ne contienne pas de topic.
     * @param idRubrique identifiant de la rubrique à supprimer.
     * @param user l'utilisateur connecté tentant la suppression.
     * @throws AccessDeniedException si l'utilisateur n'est pas admin.
     * @throws FileNotFoundException si la rubrique est introuvable ou invalide.
     * @throws BusinessRuleException si la tentative de suppression ne respecte pas les règles métier.
     */
    public void deleteRubrique(Long idRubrique, Utilisateur user)
            throws FileNotFoundException, BusinessRuleException {
        Rubrique rubriqueASupprimer = ForumUtils.findRubriqueOrThrow(rubriqueRepository, idRubrique);
        UtilisateurUtils.isAdmin(user);
        ForumUtils.assertRubriqueIsEmpty(topicRepository, idRubrique);
        rubriqueRepository.delete(rubriqueASupprimer);
    }
}
