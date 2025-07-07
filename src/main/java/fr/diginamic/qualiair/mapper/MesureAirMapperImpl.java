package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.atmofrance.AirDataPropertiesDto;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.openweather.AirDto;
import fr.diginamic.qualiair.dto.openweather.AirMeasureDto;
import fr.diginamic.qualiair.dto.openweather.LocalAirQualityDto;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.utils.MesureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.DateUtils.fromDateString;
import static fr.diginamic.qualiair.utils.DateUtils.toLocalDateTime;
import static fr.diginamic.qualiair.utils.MesureUtils.createMesureAir;

/**
 * Mapper pour les mesures qualité de l'air
 */
@Component
public class MesureAirMapperImpl implements MesureAirMapper {
    private static final Logger logger = LoggerFactory.getLogger(MesureAirMapperImpl.class);

    @Override
    public List<MesureAir> toEntityList(AirDataFeatureDto feature, LocalDateTime timeStamp) {
        List<MesureAir> mesures = new ArrayList<>();
        AirDataPropertiesDto props = feature.getProperties();

        LocalDateTime dateReleveTime = fromDateString(props.getDateEch());

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


    @Override
    public HistoriqueAirQuality toHistoriqueDto(GeographicalScope scope, String code, AirPolluant polluant, List<MesureAir> mesures) {
        HistoriqueAirQuality dto = new HistoriqueAirQuality();
        dto.setCodeElement(polluant.toString());
        for (MesureAir m : mesures) {
            if (polluant.toString().equalsIgnoreCase("atmo")) {
                dto.addIndex(m.getDateReleve(), m.getIndice());
            } else {
                dto.addIndex(m.getDateReleve(), m.getValeur());
            }
        }
        return dto;
    }

    @Override
    public AlerteInfo toAlerteDto(MesureAir mesure) {
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

    @Override
    public List<MesureAir> toEntityList(LocalAirQualityDto dto, Coordonnee coordonnee, LocalDateTime timestamp) {
        List<MesureAir> mesures = new ArrayList<>();

        AirDto dtoMesures = dto.getList().getFirst();

        AirMeasureDto values = dtoMesures.getComponents();

        LocalDateTime dateReleveTime = toLocalDateTime(dtoMesures.getDt());
        if (dtoMesures.getMain().getAqi() != null) {
            mesures.add(createMesureAir("ATMO", dtoMesures.getMain().getAqi(), dateReleveTime, timestamp));
        }
        if (values.getCo() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("CO", "μg/m³", values.getCo(), dateReleveTime, timestamp));
        }
        if (values.getNo() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("NO", "μg/m³", values.getNo(), dateReleveTime, timestamp));
        }
        if (values.getNo2() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("NO2", "μg/m³", values.getNo2(), dateReleveTime, timestamp));
        }
        if (values.getO3() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("O3", "μg/m³", values.getO3(), dateReleveTime, timestamp));
        }
        if (values.getSo2() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("SO2", "μg/m³", values.getSo2(), dateReleveTime, timestamp));
        }
        if (values.getPm2_5() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("PM2.5", "μg/m³", values.getPm2_5(), dateReleveTime, timestamp));
        }
        if (values.getPm10() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("PM10", "μg/m³", values.getPm10(), dateReleveTime, timestamp));
        }
        if (values.getNh3() != null) {
            mesures.add(MesureUtils.createMesureAirWithValue("NH3", "μg/m³", values.getNh3(), dateReleveTime, timestamp));
        }
        mesures.forEach(m -> m.setCoordonnee(coordonnee));

        return mesures;
    }

}
