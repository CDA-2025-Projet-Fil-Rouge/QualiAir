package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.*;
import fr.diginamic.qualiair.parser.CsvParser;
import fr.diginamic.qualiair.repository.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class RecensementParserService {

    @Autowired
    private CsvParser parser;

    @Autowired
    private CoordonneeService coordonneeService;
    @Autowired
    private CommuneService communeService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private DepartementService departementService;
    @Autowired
    private CommuneMapper communeMapper;
    @Autowired
    private DepartementMapper departementMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private CoordonneeMapper coordonneeMapper;
    @Autowired
    private MesurePopulationService mesurePopulationService;
    @Autowired
    private MesureMapper mesureMapper;
    @Autowired
    private RecensementCsvMapper recensementCsvMapper;

    @Value("${recensement.fichier.communes-with-pop.path}")
    private String pathFichierPop;
    @Value("${recensement.fichier.communes-with-coord.path}")
    private String pathFichierCoord;
    @Autowired
    private MesureRepository mesureRepository;

    @Transactional
    public void saveCommunesFromFichier() throws IOException, FileNotFoundException {
        validateFilePaths();

        List<CommuneCoordDto> coordDtos = parseCoordFile(pathFichierCoord);
        saveEntitiesFromCoordDtos(coordDtos);

        List<CommuneHabitantDto> popDtos = parsePopFile(pathFichierPop);
        savePopulationFromDtos(popDtos);
    }

    private void validateFilePaths() throws FileNotFoundException {
        if (pathFichierCoord == null) {
            throw new FileNotFoundException("Missing coordinate file");
        }
        if (pathFichierPop == null) {
            throw new FileNotFoundException("Missing population file");
        }
    }

    private List<CommuneCoordDto> parseCoordFile(String path) throws IOException {
        List<String> lines = parser.parseFile(path);
        lines.removeFirst(); // skip header
        return lines.stream()
                .map(line -> line.split(","))
                .filter(tokens -> tokens.length >= 14)
                .map(recensementCsvMapper::mapToCommuneCoordDto)
                .toList();
    }

    private List<CommuneHabitantDto> parsePopFile(String path) throws IOException {
        List<String> lines = parser.parseFile(path);
        lines.removeFirst(); // skip header
        return lines.stream()
                .map(line -> line.split(";"))
                .filter(tokens -> tokens.length >= 9)
                .map(recensementCsvMapper::mapToCommuneHabitantDto)
                .toList();
    }

    private void saveEntitiesFromCoordDtos(List<CommuneCoordDto> dtos) {
        for (CommuneCoordDto dto : dtos) {
            Region region = regionService.findOrCreate(regionMapper.toEntityFromCommuneCoordDto(dto));

            Departement departement = departementMapper.toEntityFromCommuneCoordDto(dto);
            departement.setRegion(region);
            departement = departementService.findOrCreate(departement);

            Coordonnee coordonnee = null;
            try {
                coordonnee = coordonneeMapper.toEntityFromCommuneCoordDto(dto);
                coordonnee = coordonneeService.findOrCreate(coordonnee);
            } catch (Exception e) {
                continue;
            }

            Commune commune = communeMapper.toEntityFromCommuneCoordDto(dto);
            commune.setDepartement(departement);
            commune.setCoordonnee(coordonnee);
            communeService.findOrCreate(commune);
        }
    }

    private void savePopulationFromDtos(List<CommuneHabitantDto> dtos) {
        for (CommuneHabitantDto dto : dtos) {

            String name = dto.getNomCommune();

            Commune commune = communeService.getFromCache(name);
            if (commune == null) {
                continue;
            }

            MesurePopulation mesure = mesureMapper.toEntityFromCommuneCoordDto(dto);

            mesure.setCoordonnee(commune.getCoordonnee());

            mesurePopulationService.save(mesure);
        }
    }
}
