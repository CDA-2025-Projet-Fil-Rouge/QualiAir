package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.diginamic.qualiair.utils.MesureUtils.toDouble;

@Component
public class MesurePrevisionMapperImpl implements MesurePrevisionMapper {
    private static final Logger logger = LoggerFactory.getLogger(MesurePrevisionMapperImpl.class);


    @Override
    public List<MesurePrevision> toEntityList(CurrentForecastDto dto, TypeReleve typeReleve, LocalDateTime timeStamp, Coordonnee coordonnee) {

        List<MesurePrevision> mesures = new ArrayList<>();
        LocalDateTime datePrevision = DateUtils.toLocalDateTime(dto.getDt());

        Temperature temp = dto.getMain();
        if (temp != null) {
            addIfNotNull(mesures, temp.getTemp(), typeReleve, datePrevision, NatureMesurePrevision.TEMPERATURE);
            addIfNotNull(mesures, temp.getFeels_like(), typeReleve, datePrevision, NatureMesurePrevision.TEMPERATURE_FELT);
            addIfNotNull(mesures, temp.getTemp_max(), typeReleve, datePrevision, NatureMesurePrevision.TEMPERATURE_MAX);
            addIfNotNull(mesures, temp.getTemp_min(), typeReleve, datePrevision, NatureMesurePrevision.TEMPERATURE_MIN);
            addIfNotNull(mesures, temp.getHumidity(), typeReleve, datePrevision, NatureMesurePrevision.HUMIDITY);
            addIfNotNull(mesures, temp.getPressure(), typeReleve, datePrevision, NatureMesurePrevision.PRESSURE);
        }

        addIfNotNull(mesures, dto.getVisibility(), typeReleve, datePrevision, NatureMesurePrevision.VISIBILITY);

        Wind wind = dto.getWind();
        if (wind != null) {
            addIfNotNull(mesures, wind.getSpeed(), typeReleve, datePrevision, NatureMesurePrevision.WIND_SPEED);
            addIfNotNull(mesures, wind.getGust(), typeReleve, datePrevision, NatureMesurePrevision.WIND_SPEED_GUST);
            addIfNotNull(mesures, wind.getDeg(), typeReleve, datePrevision, NatureMesurePrevision.WIND_ORIENTATION);
        }

        Clouds clouds = dto.getClouds();
        if (clouds != null) {
            addIfNotNull(mesures, clouds.getAll(), typeReleve, datePrevision, NatureMesurePrevision.CLOUD_COVERAGE);
        }

        Rain rain = dto.getRain();
        if (rain != null) {
            addIfNotNull(mesures, rain.getRainOneHour(), typeReleve, datePrevision, NatureMesurePrevision.RAIN_1H);
            addIfNotNull(mesures, rain.getRainThreeHour(), typeReleve, datePrevision, NatureMesurePrevision.RAIN_3H);
        }

        Snow snow = dto.getSnow();
        if (snow != null) {
            addIfNotNull(mesures, snow.getSnowOneH(), typeReleve, datePrevision, NatureMesurePrevision.SNOW_1H);
            addIfNotNull(mesures, snow.getSnowThreeH(), typeReleve, datePrevision, NatureMesurePrevision.SNOW_3H);
        }

        return mesures;
    }

    @Override
    public List<MesurePrevision> toEntityListFromCurrentWeather(CurrentForecastDto dto, LocalDateTime timeStamp, Coordonnee coordonnee) {
        LocalDateTime dateReleve = DateUtils.toLocalDateTime(dto.getDt());
        Mesure base = new Mesure(TypeMesure.RELEVE_METEO, timeStamp, dateReleve, coordonnee);

        List<MesurePrevision> mesures = toEntityList(dto, TypeReleve.ACTUEL, timeStamp, coordonnee);
        mesures.forEach(mprev -> mprev.setMesure(base));

        return mesures;
    }

    @Override
    public List<MesurePrevision> toEntityListFromFiveDaysForecast(ForecastFiveDayDto dto, LocalDateTime timeStamp, Coordonnee coordonnee) {
        Mesure base = new Mesure(TypeMesure.RELEVE_METEO, timeStamp, LocalDateTime.now(), coordonnee);

        List<MesurePrevision> mesures = new ArrayList<>();
        for (CurrentForecastDto dailyForecast : dto.getList()) {
            mesures.addAll(toEntityList(dailyForecast, TypeReleve.PREVISION_5J, timeStamp, coordonnee));
        }
        mesures.forEach(mprev -> mprev.setMesure(base));
        return mesures;
    }

    @Override
    public List<MesurePrevision> toEntityListFromSixteenDaysForecast(ForecastSixteenDays dto, LocalDateTime timeStamp, Coordonnee coordonnee) {
        throw new UnsupportedOperationException("Not supported yet");
        // todo impl
    }

    @Override
    public HistoriquePrevision toHistoricalDto(GeographicalScope scope, String code, NatureMesurePrevision nature, List<MesurePrevision> mesures) {
        return createHistoriqueDto(scope, code, nature, mesures, false);
    }

    @Override
    public HistoriquePrevision toHistoricalDtoFromRegion(GeographicalScope scope, String code, NatureMesurePrevision nature, List<MesurePrevision> mesures) {
        return createHistoriqueDto(scope, code, nature, mesures, true);
    }

    @Override
    public HistoriquePrevision toHistoricalDtoFromDepartement(GeographicalScope scope, String code, NatureMesurePrevision nature, List<MesurePrevision> mesures) {
        return createHistoriqueDto(scope, code, nature, mesures, true);
    }

    private HistoriquePrevision createHistoriqueDto(GeographicalScope scope, String code, NatureMesurePrevision nature, List<MesurePrevision> mesures, boolean shouldAverage) {
        HistoriquePrevision dto = new HistoriquePrevision();
        dto.setScope(scope.toString());
        dto.setCode(code);
        dto.setNature(nature.toString());

        if (shouldAverage) {
            Map<LocalDateTime, Double> averagesByHour = mesures.stream()
                    .collect(Collectors.groupingBy(
                            m -> m.getMesure().getDateReleve().truncatedTo(ChronoUnit.HOURS),
                            Collectors.averagingDouble(MesurePrevision::getValeur)
                    ));

            averagesByHour.forEach(dto::addValeur);
        } else {
            for (MesurePrevision m : mesures) {
                LocalDateTime hourTruncated = m.getMesure().getDateReleve().truncatedTo(ChronoUnit.HOURS);
                double value = m.getValeur();

                dto.addValeur(hourTruncated, value);
            }
        }
        return dto;
    }

    /**
     * Crée une mesure de prévision météo.
     *
     * @param typeReleve type de relevé
     * @param valeur     valeur brute
     * @param nature     nature de la mesure
     * @return la mesure construite
     * @throws ParsedDataException si la valeur n'est pas convertible
     */
    private MesurePrevision createMesurePrevision(TypeReleve typeReleve, String valeur, LocalDateTime datePrevision, NatureMesurePrevision nature) throws ParsedDataException {
        MesurePrevision mesure = new MesurePrevision();

        mesure.setTypeReleve(typeReleve);
        mesure.setDatePrevision(datePrevision);
        mesure.setValeur(toDouble(valeur));
        setNatureAndUnite(mesure, nature);
        return mesure;
    }

    /**
     * Définit la nature et l'unité en fonction de la nature de la mesure.
     *
     * @param mesure mesure à modifier
     * @param nature nature de la mesure
     */
    private void setNatureAndUnite(MesurePrevision mesure, NatureMesurePrevision nature) {
        mesure.setNature(nature.toString());
        switch (nature) {
            case HUMIDITY, CLOUD_COVERAGE -> mesure.setUnite("%");
            case TEMPERATURE, TEMPERATURE_MAX, TEMPERATURE_FELT, TEMPERATURE_MIN, WIND_ORIENTATION ->
                    mesure.setUnite("°");
            case PRESSURE -> mesure.setUnite("hpa");
            case VISIBILITY -> mesure.setUnite("m");
            case WIND_SPEED, WIND_SPEED_GUST -> mesure.setUnite("m/s");
            case RAIN_1H, RAIN_3H, SNOW_1H, SNOW_3H -> mesure.setUnite("mm/h");
            default -> mesure.setUnite("tbd");
        }
    }

    /**
     * Ajoute une {@link MesurePrevision} à une liste si la valeur est non nulle.
     *
     * @param mesures liste cible
     * @param valeur  valeur de la mesure
     * @param type    type de relevé
     * @param nature  nature de la mesure
     */
    private void addIfNotNull(List<MesurePrevision> mesures, String valeur, TypeReleve type, LocalDateTime datePrevision, NatureMesurePrevision nature) {
        try {
            if (valeur != null) {
                mesures.add(createMesurePrevision(type, valeur, datePrevision, nature));
            }
        } catch (ParsedDataException e) {
            logger.debug("Couldn't convert value to double for {}", type);
        }
    }

}
