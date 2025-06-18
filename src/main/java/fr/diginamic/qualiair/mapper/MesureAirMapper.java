package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.atmofrance.AirDataPropertiesDto;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.entity.TypeMesure;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.DateUtils.toLocalDateTime;
import static fr.diginamic.qualiair.utils.MesureUtils.cleanUpElementCode;

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

    /**
     * Crée une mesure air
     *
     * @param codeElement code de l'élément récupéré par la requete
     * @param indice      valeur indexée de la mesure (convention //todo trouver la convention euro d'indexation
     * @param dateReleve  date du relevé
     * @param timeStamp   date d'insertion
     * @return mesure créée
     */
    private MesureAir createMesureAir(String codeElement, String indice,
                                      LocalDateTime dateReleve, LocalDateTime timeStamp) {
        MesureAir mesure = new MesureAir();
        mesure.setTypeMesure(TypeMesure.RELEVE_AIR);
        mesure.setCodeElement(cleanUpElementCode(codeElement));
        mesure.setIndice(Integer.parseInt(indice));
        mesure.setDateReleve(dateReleve);
        mesure.setDateEnregistrement(timeStamp);

        return mesure;
    }

    public HistoriqueAirQuality toHistoricalDto(AirPolluant polluant, List<MesureAir> mesures) {
        HistoriqueAirQuality dto = new HistoriqueAirQuality();
        dto.setCodeElement(polluant.toString());
        for (MesureAir m : mesures) {
            dto.addIndex(m.getDateReleve(), m.getIndice());
        }
        return dto;
    }
}
