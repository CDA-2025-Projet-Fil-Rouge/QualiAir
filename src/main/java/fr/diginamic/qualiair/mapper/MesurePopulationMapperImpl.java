package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.Mesure;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.entity.TypeMesure;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.diginamic.qualiair.utils.MesureUtils.toInt;

/**
 * Mesure Mapper pour les mesures population
 */
@Component
public class MesurePopulationMapperImpl implements MesurePopulationMapper {

    @Override
    public MesurePopulation toEntityFromCsv(CommuneHabitantDto dto, LocalDateTime dateReleve, LocalDateTime timeStamp, Coordonnee coordonnee) throws ParsedDataException {
        Mesure base = new Mesure(TypeMesure.RELEVE_POPULATION, timeStamp, dateReleve, coordonnee);
        MesurePopulation mpop = new MesurePopulation();
        mpop.setValeur(toInt(dto.getPopulationMunicipale().trim()));
        mpop.setMesure(base);
        return mpop;
    }

    @Override
    public HistoriquePopulation toHistoricalDto(GeographicalScope scope, String code, List<MesurePopulation> mesures) {
        return createHistoriqueDto(scope, code, mesures, false);
    }

    @Override
    public HistoriquePopulation toHistoricalDtoFromRegion(GeographicalScope scope, String codeRegion, List<MesurePopulation> mesures) {
        return createHistoriqueDto(scope, codeRegion, mesures, true);

    }

    @Override
    public HistoriquePopulation toHistoricalDtoFromDepartement(GeographicalScope scope, String codeDept, List<MesurePopulation> mesures) {
        return createHistoriqueDto(scope, codeDept, mesures, true);
    }

    private HistoriquePopulation createHistoriqueDto(GeographicalScope scope, String code, List<MesurePopulation> mesures, boolean shouldAverage) {
        HistoriquePopulation dto = new HistoriquePopulation();
        dto.setScope(scope.toString());
        dto.setCode(code);


        if (shouldAverage) {
            Map<LocalDateTime, Double> averagesByHour = mesures.stream()
                    .collect(Collectors.groupingBy(
                            m -> m.getMesure().getDateReleve().truncatedTo(ChronoUnit.HOURS),
                            Collectors.averagingInt(MesurePopulation::getValeur)
                    ));

            averagesByHour.forEach(dto::addIndex);
        } else {
            for (MesurePopulation m : mesures) {
                LocalDateTime hourTruncated = m.getMesure().getDateReleve().truncatedTo(ChronoUnit.HOURS);
                int value = m.getValeur();

                dto.addIndex(hourTruncated, value);
            }
        }
        return dto;
    }
}
