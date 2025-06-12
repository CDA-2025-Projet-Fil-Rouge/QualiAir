package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.mapper.forumMapper.RubriqueMapper;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
