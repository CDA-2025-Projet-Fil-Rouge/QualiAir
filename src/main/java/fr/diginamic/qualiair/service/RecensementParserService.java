package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.dto.insertion.DtoFromCsv;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.mapper.CoordonneeMapper;
import fr.diginamic.qualiair.mapper.DepartementMapper;
import fr.diginamic.qualiair.mapper.RegionMapper;
import fr.diginamic.qualiair.parser.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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
    private CacheService cacheService;
    @Autowired
    private CommuneMapper communeMapper;
    @Autowired
    private DepartementMapper departementMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private CoordonneeMapper coordonneeMapper;
    @Value("${recensement.fichier.communes-with-pop.path}")
    private String pathFichier1;
    @Value("${recensement.fichier.communes-with-coord.path}")
    private String pathFichier2;


    // could go to a mapper
    private CommuneCoordDto getFichierCommuneCoordDto(String[] tokens) {
        CommuneCoordDto dto = new CommuneCoordDto();
        dto.setCodeCommuneINSEE(tokens[0]);
        dto.setNomCommunePostal(tokens[1]);
        dto.setCodePostal(tokens[2]);
        dto.setLatitude(tokens[5]);
        dto.setLongitude(tokens[6]);
        dto.setNomCommuneComplet(tokens[10]);
        dto.setCodeDepartement(tokens[11]);
        dto.setNomDepartement(tokens[12]);
        dto.setCodeRegion(tokens[13]);
        dto.setNomRegion(tokens[14]);
        return dto;
    }

    // could go to a mapper
    private CommuneHabitantDto getFichierCommuneHabitantDto(String[] tokens) {
        CommuneHabitantDto dto = new CommuneHabitantDto();
        dto.setNomCommune(tokens[6]);
        dto.setPopulationTotale(tokens[7]);
        return dto;
    }

    private List<DtoFromCsv> parseListVilleFromFichier(String pathFichier) throws IOException, FileNotFoundException {
        List<String> lines = parser.parseFile(pathFichier);
        lines.removeFirst(); //skip header

        List<DtoFromCsv> list = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split(",");
            if (pathFichier.equals(pathFichier1)) {

                CommuneCoordDto dto = getFichierCommuneCoordDto(tokens);
                list.add(dto);
            }
            if (pathFichier.equals(pathFichier2)) {
                CommuneHabitantDto dto = getFichierCommuneHabitantDto(tokens);
                list.add(dto);
            } else {
                throw new FileNotFoundException("File not found for this path: " + pathFichier);
            }
        }

        return list;
    }

    @Transactional
    public void saveCommunesFromFichier() throws FileNotFoundException, IOException {

        if (pathFichier1 == null) {
            throw new FileNotFoundException("Couldn't find file 1");
        }
        if (pathFichier2 == null) {
            throw new FileNotFoundException("Couldn't find file 2");
        }

        List<DtoFromCsv> fichier1List = parseListVilleFromFichier(pathFichier1);

        for (DtoFromCsv dto : fichier1List) {

            Region region = regionMapper.toEntityFromCommuneCoordDto(dto);
            regionService.findOrCreate(region);

            Departement departement = departementMapper.toEntityFromCommuneCoordDto(dto);
            departement.setRegion(region);
            departementService.findOrCreate(departement);

            Coordonnee coordonnee = coordonneeMapper.toEntityFromCommuneCoordDto(dto);
            coordonneeService.findOrCreate(coordonnee);

            Commune commune = communeMapper.toEntityFromCommuneCoordDto(dto);
            commune.setCoordonnee(coordonnee);
            commune.setDepartement(departement);
            communeService.findOrCreate(commune);
        }

        List<DtoFromCsv> fichier2List = parseListVilleFromFichier(pathFichier2);
        for (DtoFromCsv dto : fichier2List) {
            Commune commune = communeMapper.toEntityFromCommuneHabitantDto(dto);
            communeService.updateByName(commune);
        }


    }

}
