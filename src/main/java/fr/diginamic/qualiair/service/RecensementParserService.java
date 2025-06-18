package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.dto.insertion.DepartementDto;
import fr.diginamic.qualiair.dto.insertion.RegionDto;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.mapper.*;
import fr.diginamic.qualiair.parser.CsvParser;
import fr.diginamic.qualiair.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Recensement Parser Service
 * Contains methods to parse, map and persist entities from csv files
 */
@Service
public class RecensementParserService {
    private static final Logger logger = LoggerFactory.getLogger(RecensementParserService.class);

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
    private MesurePopulationMapper mesurePopulationMapper;
    @Autowired
    private RecensementCsvMapper recensementCsvMapper;

    @Value("${recensement.fichier.communes-with-pop.path}")
    private String pathFichierPop;
    @Value("${recensement.fichier.communes-with-coord.path}")
    private String pathFichierCoord;
    @Value("${recensement.fichier.departement.path}")
    private String pathFichierDepartement;
    @Value("${recensement.fichier.region.path}")
    private String pathFichierRegion;

    @Transactional
    public void saveCommunesFromFichier() throws IOException, FileNotFoundException {
        validateFilePaths();
        cacheService.loadExistingCommunesWithRelations();

        logger.debug("Starting insertion");

        List<DepartementDto> departementDtos = parseDepartementFile(pathFichierDepartement);
        List<RegionDto> regionDtos = parseRegionFile(pathFichierRegion);
        saveRegionAndDepartement(departementDtos, regionDtos);

        List<CommuneCoordDto> coordDtos = parseCoordFile(pathFichierCoord);
        saveCommunesAndCoords(coordDtos);

        List<CommuneHabitantDto> popDtos = parsePopFile(pathFichierPop);
        String filename = Paths.get(pathFichierPop).getFileName().toString();
        String fileDate = filename.split("_")[0];
        savePopulationFromDtos(popDtos, fileDate);

        logger.debug("Completed insertion");
        cacheService.clearCaches();
    }

    private List<RegionDto> parseRegionFile(String path) throws IOException {
        return CsvParser.parseRecensementCsv(path, true, ",", recensementCsvMapper::mapToRegionDto);
    }

    private List<DepartementDto> parseDepartementFile(String path) throws IOException {

        return CsvParser.parseRecensementCsv(path, true, ",", recensementCsvMapper::mapToDepartementdto);
    }

    private List<CommuneCoordDto> parseCoordFile(String path) throws IOException {
        return CsvParser.parseRecensementCsv(path, true, ",", recensementCsvMapper::mapToCommuneCoordDto);
    }

    private List<CommuneHabitantDto> parsePopFile(String path) throws IOException {
        return CsvParser.parseRecensementCsv(path, false, ",", recensementCsvMapper::mapToCommuneHabitantDto);
    }

    private void validateFilePaths() throws FileNotFoundException {
        if (pathFichierCoord == null) throw new FileNotFoundException("Missing coordinate file");
        if (pathFichierPop == null) throw new FileNotFoundException("Missing population file");
        if (pathFichierRegion == null) throw new FileNotFoundException("Missing region file");
        if (pathFichierDepartement == null) throw new FileNotFoundException("Missing departement file");
    }

    private void saveCommunesAndCoords(List<CommuneCoordDto> communeDtos) {
        for (CommuneCoordDto dto : communeDtos) {
            Commune commune = communeMapper.toEntityFromCommuneCoordDto(dto);

            Departement dept = cacheService.findInDepartementCache(dto.getCodeDepartement());
            commune.setDepartement(dept);
            Coordonnee coordonnee;
            try {
                coordonnee = coordonneeMapper.toEntityFromCommuneCoordDto(dto);
            } catch (ParsedDataException e) {
                logger.debug("Skipping {}, couldn't parse coordinates {} / {} \n {}", commune.getNomSimple(), dto.getLatitude(), dto.getLongitude(), e.getMessage());
                continue;
            }

            commune.setCoordonnee(coordonnee);
            coordonnee.setCommune(commune);

            coordonneeService.findOrCreate(coordonnee);
            communeService.findOrCreate(commune);
        }
    }

    private void saveRegionAndDepartement(List<DepartementDto> departementDtos, List<RegionDto> regionDtos) {
        Map<String, Region> regionsById = new HashMap<>();
        for (RegionDto regionDto : regionDtos) {
            Region region = regionMapper.toEntity(regionDto);
            Region saved = regionService.findOrCreate(region);
            regionsById.put(regionDto.getId(), saved);
        }

        for (DepartementDto departementDto : departementDtos) {
            Departement departement = departementMapper.toEntity(departementDto);
            Region region = regionsById.get(departementDto.getRegionId());
            departement.setRegion(region);
            departementService.findOrCreate(departement);
        }
    }

    private void savePopulationFromDtos(List<CommuneHabitantDto> dtos, String dateReleve) {
        LocalDate date = DateUtils.toLocalDate(dateReleve);
        if (mesurePopulationService.existByDateReleve(date)) {
            logger.debug("Population data for date {} already exists, skipping insertion", dateReleve);
            return;
        }

        for (CommuneHabitantDto dto : dtos) {
            String codeInsee = dto.getCodeInsee();
            Commune commune = communeService.getFromCache(codeInsee);
            if (commune == null) {
//                logger.debug("Commune not present for code {}", codeInsee);
                continue;
            }
            MesurePopulation mesurePopulation;
            try {
                mesurePopulation = mesurePopulationMapper.toEntity(dto, date.atStartOfDay());
            } catch (ParsedDataException e) {
                logger.debug("Skipping mesure for {}, couldn't parse value {} \n{}", commune.getNomSimple(), dto.getPopulationMunicipale(), e.getMessage());
                continue;
            }
            mesurePopulation.setCoordonnee(commune.getCoordonnee());

            mesurePopulationService.save(mesurePopulation);
        }
    }
}
