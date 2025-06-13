package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.mapper.forumMapper.RubriqueMapper;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RubriqueService {

    @Autowired
    RubriqueRepository rubriqueRepository;
    @Autowired
    RubriqueMapper rubriqueMapper;

    public List<RubriqueDto> getAllRubriques() {
        return rubriqueRepository.findAll().stream()
                .map(rubrique -> rubriqueMapper.toDto(rubrique))
                .toList();
    }

    public RubriqueDto createRubrique(RubriqueDto dto, Utilisateur createur) {
        if (!createur.getRole().equals(RoleUtilisateur.ADMIN)) {
            throw new AccessDeniedException("Seuls les administrateurs peuvent créer une rubrique.");
        }
        Rubrique rubrique = rubriqueMapper.toEntity(dto);
        rubrique.setCreateur(createur);
        rubrique.setDateCreation(LocalDateTime.now());
        rubriqueRepository.save(rubrique);
        return rubriqueMapper.toDto(rubrique);
    }
}
