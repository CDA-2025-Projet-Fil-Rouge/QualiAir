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
import java.util.List;

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
        HistoriquePopulation dto = new HistoriquePopulation();
        MesurePopulation mpop = mesures.getFirst();
        dto.setNom(mpop.getMesure().getCoordonnee().getCommune().getNomSimple());
        for (MesurePopulation m : mesures) {
            dto.addIndex(m.getMesure().getDateEnregistrement(), m.getValeur());
        }
        return dto;
    }
}
