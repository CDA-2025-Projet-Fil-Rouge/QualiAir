package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.forumMapper.RubriqueMapper;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.RubriqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service de gestion des rubriques du forum.
 * Gère la récupération, la création et les règles métier associées à la gestion des rubriques.
 * Seule un utilisateur ayant le rôle ADMIN peut créer une nouvelle rubrique.
 */
@Service
public class RubriqueServiceImpl implements RubriqueService {

    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Autowired
    private RubriqueMapper rubriqueMapper;
    @Autowired
    private RubriqueValidator rubriqueValidator;
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Page<RubriqueDto> getAllRubriques(Pageable pageable) {
        return rubriqueRepository.findAll(pageable).map(rubriqueMapper::toDto);
    }

    @Override
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

    @Override
    public RubriqueDto updateRubrique(Long idRubrique, RubriqueDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {

        CheckUtils.ensureMatchingIds(idRubrique, dto.getId());
        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, idRubrique);
        UtilisateurUtils.isAdmin(modificateur);

        rubrique.setNom(dto.getNom());
        rubrique.setDateModification(LocalDateTime.now());
        rubrique.setModificateur(modificateur);
        rubriqueValidator.validate(rubrique);
        rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }

    @Override
    public void deleteRubrique(Long idRubrique, Utilisateur user)
            throws FileNotFoundException, BusinessRuleException {
        Rubrique rubriqueASupprimer = ForumUtils.findRubriqueOrThrow(rubriqueRepository, idRubrique);
        UtilisateurUtils.isAdmin(user);
        ForumUtils.assertRubriqueIsEmpty(topicRepository, idRubrique);
        rubriqueRepository.delete(rubriqueASupprimer);
    }
}
