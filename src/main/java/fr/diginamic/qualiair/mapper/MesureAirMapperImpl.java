package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.atmofrance.AirDataPropertiesDto;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.openweather.AirDto;
import fr.diginamic.qualiair.dto.openweather.AirMeasureDto;
import fr.diginamic.qualiair.dto.openweather.LocalAirQualityDto;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.FunctionnalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.AirPolluantUtils.calculateIndice;
import static fr.diginamic.qualiair.utils.DateUtils.fromDateString;
import static fr.diginamic.qualiair.utils.DateUtils.toLocalDateTime;
import static fr.diginamic.qualiair.utils.MesureUtils.cleanUpElementCode;

/**
 * Mapper pour les mesures qualité de l'air
 */
@Component
public class MesureAirMapperImpl implements MesureAirMapper {
    private static final Logger logger = LoggerFactory.getLogger(MesureAirMapperImpl.class);

    @Override
    public List<MesureAir> toEntityListFromAtmoFranceApi(AirDataFeatureDto feature, LocalDateTime timeStamp, Coordonnee coordonnee) {
        List<MesureAir> mesures = new ArrayList<>();
        AirDataPropertiesDto props = feature.getProperties();
        LocalDateTime dateReleve = fromDateString(props.getDateEch());

        Mesure base = new Mesure(TypeMesure.RELEVE_AIR, timeStamp, dateReleve, coordonnee);

        if (props.getCodeNo2() != null) {
            mesures.add(createMesureAir("NO2", props.getCodeNo2()));
        }

        if (props.getCodeO3() != null) {
            mesures.add(createMesureAir("O3", props.getCodeO3()));
        }

        if (props.getCodePm10() != null) {
            mesures.add(createMesureAir("PM10", props.getCodePm10()));
        }

        if (props.getCodePm25() != null) {
            mesures.add(createMesureAir("PM2.5", props.getCodePm25()));
        }

        if (props.getCodeSo2() != null) {
            mesures.add(createMesureAir("SO2", props.getCodeSo2()));
        }

        if (props.getCodeQual() != null) {
            mesures.add(createMesureAir("ATMO", props.getCodeQual()));
        }
        mesures.forEach(mAir -> mAir.setMesure(base));
        return mesures;
    }


    @Override
    public HistoriqueAirQuality toHistoriqueDto(GeographicalScope scope, String code, AirPolluant polluant, List<MesureAir> mesures) {
        HistoriqueAirQuality dto = new HistoriqueAirQuality();
        dto.setCodeElement(polluant.toString());
        for (MesureAir m : mesures) {
            if (polluant.toString().equalsIgnoreCase("atmo")) {
                dto.addIndex(m.getMesure().getDateReleve(), m.getIndice());
            } else {
                dto.addIndex(m.getMesure().getDateReleve(), m.getValeur());
            }
        }
        return dto;
    }

    @Override
    public AlerteInfo toAlerteDto(MesureAir mesure) {
        AlerteInfo dto = new AlerteInfo();
        Coordonnee coordonnee = mesure.getMesure().getCoordonnee();
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
    public List<MesureAir> toEntityListFromOpenWeatherApi(LocalAirQualityDto dto, Coordonnee coordonnee, LocalDateTime timestamp) {
        List<MesureAir> mesures = new ArrayList<>();
        AirDto dtoMesures = dto.getList().getFirst();
        AirMeasureDto values = dtoMesures.getComponents();
        LocalDateTime dateReleve = toLocalDateTime(dtoMesures.getDt());

        Mesure base = new Mesure(TypeMesure.RELEVE_AIR, timestamp, dateReleve, coordonnee);
        if (dtoMesures.getMain().getAqi() != null) {
            mesures.add(createMesureAir("ATMO", dtoMesures.getMain().getAqi()));
        }
        if (values.getCo() != null) {
            mesures.add(createMesureAirWithValue("CO", "μg/m³", values.getCo()));
        }
        if (values.getNo() != null) {
            mesures.add(createMesureAirWithValue("NO", "μg/m³", values.getNo()));
        }
        if (values.getNo2() != null) {
            mesures.add(createMesureAirWithValue("NO2", "μg/m³", values.getNo2()));
        }
        if (values.getO3() != null) {
            mesures.add(createMesureAirWithValue("O3", "μg/m³", values.getO3()));
        }
        if (values.getSo2() != null) {
            mesures.add(createMesureAirWithValue("SO2", "μg/m³", values.getSo2()));
        }
        if (values.getPm2_5() != null) {
            mesures.add(createMesureAirWithValue("PM2.5", "μg/m³", values.getPm2_5()));
        }
        if (values.getPm10() != null) {
            mesures.add(createMesureAirWithValue("PM10", "μg/m³", values.getPm10()));
        }
        if (values.getNh3() != null) {
            mesures.add(createMesureAirWithValue("NH3", "μg/m³", values.getNh3()));
        }
        mesures.forEach(mAir -> mAir.setMesure(base));

        return mesures;
    }

    /**
     * Crée une mesure d'air à partir de données brutes.
     *
     * @param codeElement code du polluant
     * @param indice      indice calculé
     * @return instance de {@link MesureAir}
     */
    private MesureAir createMesureAir(String codeElement, String indice
    ) {
        MesureAir mesure = new MesureAir();
        mesure.setCodeElement(cleanUpElementCode(codeElement));
        mesure.setIndice(Integer.parseInt(indice));
        return mesure;
    }

    //todo doc
    private MesureAir createMesureAirWithValue(String codeElement, String unite, double valeur) {
        MesureAir mAir = new MesureAir();
        mAir.setCodeElement(codeElement);
        mAir.setUnite(unite);
        mAir.setValeur(valeur);
        try {
            int indice = calculateIndice(codeElement, valeur);
            mAir.setIndice(indice);
        } catch (FunctionnalException e) {
            logger.debug(e.getMessage());
            mAir.setIndice(0);
        }
        return mAir;
    }

}
