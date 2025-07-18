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
import fr.diginamic.qualiair.validator.forumValidator.RubriqueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static fr.diginamic.qualiair.utils.UtilisateurUtils.isAdmin;

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
    public List<RubriqueDto> getAllRubriquesUnpaged() {
        return rubriqueRepository.findAll().stream().map(rubriqueMapper::toDto).toList();
    }

    @Override
    public RubriqueDto createRubrique(RubriqueDto dto, Utilisateur createur)
            throws BusinessRuleException, TokenExpiredException {
        if (!isAdmin(createur)) {
            throw new AccessDeniedException("Fonction réservée aux administrateurs.");
        }

        Rubrique rubrique = rubriqueMapper.toEntity(dto);
        rubrique.setCreateur(createur);
        rubrique.setDateCreation(LocalDateTime.now());

        int nextIndice = rubriqueRepository.findMaxPrioriteIndice().orElse(0) + 1;
        rubrique.setPrioriteAffichageIndice(nextIndice);

        rubriqueValidator.validate(rubrique);
        rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }

    @Override
    public RubriqueDto updateRubrique(Long idRubrique, RubriqueDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {

        CheckUtils.ensureMatchingIds(idRubrique, dto.getId());
        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, idRubrique);
        if (!isAdmin(modificateur)) {
            throw new AccessDeniedException("Fonction réservée aux administrateurs.");
        }

        rubrique.setNom(dto.getNom());
        rubrique.setDescription(dto.getDescription());
        rubrique.setDateModification(LocalDateTime.now());
        rubrique.setModificateur(modificateur);
        rubriqueValidator.validate(rubrique);
        rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }

   @Override
   public List<RubriqueDto> updatePriorities(List<RubriqueDto> rubriques, Utilisateur demandeur) throws FileNotFoundException {
        if (!isAdmin(demandeur)) {
            throw new AccessDeniedException("Fonction réservée aux administrateurs.");
        }

       // Étape 1 : attribuer une valeur temporaire pour éviter les conflits d'index uniques
       for (RubriqueDto dto : rubriques) {
           Rubrique rubrique = rubriqueRepository.findById(dto.getId())
                   .orElseThrow(() -> new FileNotFoundException("Rubrique non trouvée"));
           rubrique.setPrioriteAffichageIndice(-rubrique.getId().intValue()); // valeur temporaire unique
           rubriqueRepository.save(rubrique);
       }

       // Étape 2 : appliquer les nouvelles valeurs
        for (RubriqueDto dto : rubriques) {
            Rubrique rubrique = rubriqueRepository.findById(dto.getId())
                    .orElseThrow(() -> new FileNotFoundException("Rubrique non trouvée"));
            rubrique.setPrioriteAffichageIndice(dto.getPrioriteAffichageIndice());
            rubriqueRepository.save(rubrique);
        }
       return rubriqueRepository.findAll().stream()
               .map(rubriqueMapper::toDto)
               .toList();
    }


    @Override
    public void deleteRubrique(Long idRubrique, Utilisateur user)
            throws FileNotFoundException, BusinessRuleException {
        Rubrique rubriqueASupprimer = ForumUtils.findRubriqueOrThrow(rubriqueRepository, idRubrique);
        if (!isAdmin(user)) {
            throw new AccessDeniedException("Fonction réservée aux administrateurs.");
        }
        ForumUtils.assertRubriqueIsEmpty(topicRepository, idRubrique);
        rubriqueRepository.delete(rubriqueASupprimer);
    }
}
