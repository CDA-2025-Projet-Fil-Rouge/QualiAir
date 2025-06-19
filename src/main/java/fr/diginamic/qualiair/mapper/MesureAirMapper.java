package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.AlerteInfo;
import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.atmofrance.AirDataPropertiesDto;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.DateUtils.toLocalDateTime;
import static fr.diginamic.qualiair.utils.MesureUtils.createMesureAir;

/**
 * Mapper pour les mesures qualité de l'air
 */
@Component
public class MesureAirMapper {

    /**
     * @param feature objet réponse de l'api Atmo-France
     * @return Liste de mesures Air
     */
    public List<MesureAir> toEntityList(AirDataFeatureDto feature, LocalDateTime timeStamp) {
        List<MesureAir> mesures = new ArrayList<>();
        AirDataPropertiesDto props = feature.getProperties();

        LocalDateTime dateReleveTime = toLocalDateTime(props.getDateEch());


        if (props.getCodeNo2() != null) {
            mesures.add(createMesureAir("NO2", props.getCodeNo2(), dateReleveTime, timeStamp));
        }

        if (props.getCodeO3() != null) {
            mesures.add(createMesureAir("O3", props.getCodeO3(), dateReleveTime, timeStamp));
        }

        if (props.getCodePm10() != null) {
            mesures.add(createMesureAir("PM10", props.getCodePm10(), dateReleveTime, timeStamp));
        }

        if (props.getCodePm25() != null) {
            mesures.add(createMesureAir("PM2.5", props.getCodePm25(), dateReleveTime, timeStamp));
        }

        if (props.getCodeSo2() != null) {
            mesures.add(createMesureAir("SO2", props.getCodeSo2(), dateReleveTime, timeStamp));
        }

        if (props.getCodeQual() != null) {
            mesures.add(createMesureAir("ATMO", props.getCodeQual(), dateReleveTime, timeStamp));
        }

        return mesures;
    }


    public HistoriqueAirQuality toDto(AirPolluant polluant, List<MesureAir> mesures) {
        HistoriqueAirQuality dto = new HistoriqueAirQuality();
        dto.setCodeElement(polluant.toString());
        for (MesureAir m : mesures) {
            dto.addIndex(m.getDateReleve(), m.getIndice());
        }
        return dto;
    }

    public AlerteInfo toDto(MesureAir mesure) {
        AlerteInfo dto = new AlerteInfo();
        Coordonnee coordonnee = mesure.getCoordonnee();
        Commune commune = coordonnee.getCommune();

        dto.setCodeInsee(commune.getCodeInsee());
        dto.setCommuneNom(commune.getNomSimple());
        dto.setNomDepartement(commune.getDepartement().getNom());
        dto.setNomRegion(commune.getDepartement().getRegion().getNom());
        dto.setValeurIndice(mesure.getIndice());
        dto.setPolluant(AirPolluant.valueOf(mesure.getCodeElement()));
        return dto;
    }
}
