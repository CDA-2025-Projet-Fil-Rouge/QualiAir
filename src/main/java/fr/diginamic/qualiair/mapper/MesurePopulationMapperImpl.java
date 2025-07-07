package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
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
    public MesurePopulation toEntity(CommuneHabitantDto dto, LocalDateTime date) throws ParsedDataException {
        MesurePopulation mesure = new MesurePopulation();
        mesure.setTypeMesure(TypeMesure.RELEVE_POPULATION);
        mesure.setDateReleve(date);
        mesure.setDateEnregistrement(date);
        mesure.setValeur(toInt(dto.getPopulationMunicipale().trim()));
        return mesure;
    }

    @Override
    public HistoriquePopulation toHistoricalDto(GeographicalScope scope, String code, List<MesurePopulation> mesures) {
        HistoriquePopulation dto = new HistoriquePopulation();
        MesurePopulation mpop = mesures.getFirst();
        dto.setNom(mpop.getCoordonnee().getCommune().getNomSimple());
        for (MesurePopulation m : mesures) {
            dto.addIndex(m.getDateEnregistrement(), m.getValeur());
        }
        return dto;
    }
}
