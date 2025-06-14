package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.mapper.*;
import fr.diginamic.qualiair.parser.CsvParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * Recensement Parser Service
 * Contains methods to parse, map and persist entities from csv files
 */
@Service
public class RecensementParserService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RecensementParserService.class);
    @Autowired
    private CsvParser parser;
    @Autowired
    private CacheService cacheService;
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

    /**
     * File path for file with cities + population
     */
    @Value("${recensement.fichier.communes-with-pop.path}")
    private String pathFichierPop;
    /**
     * File path for file with cities + coordinates
     */
    @Value("${recensement.fichier.communes-with-coord.path}")
    private String pathFichierCoord;
    @Autowired
    private MesureService mesureService;

    /**
     * Recensement Parser service orchestrator
     *
     * @throws IOException           error parsing the file
     * @throws FileNotFoundException error finding the file
     */
    @Transactional
    public void saveCommunesFromFichier() throws IOException, FileNotFoundException, ParsedDataException {
        validateFilePaths();
        cacheService.loadAllCaches();

        logger.debug("Starting insertion");
        System.out.println("Starting insertion");

        List<CommuneCoordDto> coordDtos = parseCoordFile(pathFichierCoord);
        saveEntitiesFromCoordDtos(coordDtos);

        List<CommuneHabitantDto> popDtos = parsePopFile(pathFichierPop);
        savePopulationFromDtos(popDtos);

        logger.debug("Completed insertion");
        System.out.println("Completed insertion");

        cacheService.clearCaches();
    }

    /**
     * Confirms the file exists
     *
     * @throws FileNotFoundException file not found
     */
    private void validateFilePaths() throws FileNotFoundException {
        if (pathFichierCoord == null) {
            throw new FileNotFoundException("Missing coordinate file");
        }
        if (pathFichierPop == null) {
            throw new FileNotFoundException("Missing population file");
        }
    }

    /**
     * Parses the coordinate file and maps it to dtos
     *
     * @param path file path
     * @return List of dtos
     * @throws IOException exception parsing the file
     */
    private List<CommuneCoordDto> parseCoordFile(String path) throws IOException {
        List<String> lines = parser.parseFile(path);
        lines.removeFirst(); // skip header
        return lines.stream()
                .map(line -> line.split(","))
                .filter(tokens -> tokens.length >= 14)
                .map(recensementCsvMapper::mapToCommuneCoordDto)
                .toList();
    }

    /**
     * Parses the pop fils and maps it to dtos
     *
     * @param path file paths
     * @return list of dtos
     * @throws IOException exception parsing the file
     */
    private List<CommuneHabitantDto> parsePopFile(String path) throws IOException {
        List<String> lines = parser.parseFile(path);
        lines.removeFirst(); // skip header
        return lines.stream()
                .map(line -> line.split(";"))
                .filter(tokens -> tokens.length >= 9)
                .map(recensementCsvMapper::mapToCommuneHabitantDto)
                .toList();
    }

    /**
     * This method iterates over the list of dtos, creates Region, Departement Coordonees and Commune entities while setting up their relationships and persists them
     *
     * @param dtos list of dtos
     */
    private void saveEntitiesFromCoordDtos(List<CommuneCoordDto> dtos) throws ParsedDataException {
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

    /**
     * This method iterates over the list of dtos, creates MesurePopulation entities while setting up their relationships and persists them
     *
     * @param dtos list of dtos
     */
    private void savePopulationFromDtos(List<CommuneHabitantDto> dtos) throws ParsedDataException {
        for (CommuneHabitantDto dto : dtos) {

            String name = dto.getNomCommune();

            Commune commune = communeService.getFromCache(name);
            if (commune == null) {
                continue;
            }

            MesurePopulation mesurePopulation = mesureMapper.toEntityFromCommuneCoordDto(dto);
            mesurePopulation.setCoordonnee(commune.getCoordonnee());

            mesurePopulationService.findOrCreate(mesurePopulation);
        }
    }
}
