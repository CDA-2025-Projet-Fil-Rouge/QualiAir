package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.atmofrance.AirDataPropertiesDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.entity.TypeMesure;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.MesureUtils.cleanUpElementCode;
import static fr.diginamic.qualiair.utils.MesureUtils.toInt;

/**
 * Mesure Mapper
 */
@Component
public class MesureMapper {

    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    public MesurePopulation toEntity(CommuneHabitantDto dto) throws ParsedDataException {
        MesurePopulation mesure = new MesurePopulation();
        mesure.setTypeMesure(TypeMesure.RELEVE_POPULATION);
        mesure.setDateReleve(LocalDateTime.now());
        mesure.setDateEnregistrement(LocalDateTime.now());
        mesure.setValeur(toInt(dto.getPopulationMunicipale().trim().replace(" ", "")));
        return mesure;
    }

    public List<MesureAir> toEntityList(AirDataFeatureDto feature) {
        List<MesureAir> mesures = new ArrayList<>();
        AirDataPropertiesDto props = feature.getProperties();

        LocalDateTime dateReleve = LocalDate.parse(props.getDateEch()).atStartOfDay();
        LocalDateTime dateMaj = LocalDateTime.now();

        if (props.getCodeNo2() != null) {
            mesures.add(createMesureAir("NO2", props.getCodeNo2(), dateReleve, dateMaj));
        }

        if (props.getCodeO3() != null) {
            mesures.add(createMesureAir("O3", props.getCodeO3(), dateReleve, dateMaj));
        }

        if (props.getCodePm10() != null) {
            mesures.add(createMesureAir("PM10", props.getCodePm10(), dateReleve, dateMaj));
        }

        if (props.getCodePm25() != null) {
            mesures.add(createMesureAir("PM2.5", props.getCodePm25(), dateReleve, dateMaj));
        }

        if (props.getCodeSo2() != null) {
            mesures.add(createMesureAir("SO2", props.getCodeSo2(), dateReleve, dateMaj));
        }

        if (props.getCodeQual() != null) {
            mesures.add(createMesureAir("ATMO", props.getCodeQual(), dateReleve, dateMaj));
        }

        return mesures;
    }

    private MesureAir createMesureAir(String codeElement, String indice,
                                      LocalDateTime dateReleve, LocalDateTime dateMaj) {
        MesureAir mesure = new MesureAir();
        mesure.setTypeMesure(TypeMesure.RELEVE_AIR);
        mesure.setCodeElement(cleanUpElementCode(codeElement));
        mesure.setIndice(Integer.parseInt(indice));
        mesure.setDateReleve(dateReleve);
        mesure.setDateEnregistrement(dateMaj);

        return mesure;
    }
}
