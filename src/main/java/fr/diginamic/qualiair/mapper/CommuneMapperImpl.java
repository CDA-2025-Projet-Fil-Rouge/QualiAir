package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommuneDetailMeteo;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommuneDetailQualiteAir;
import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.DescriptionMeteo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static fr.diginamic.qualiair.utils.MesureUtils.*;

/**
 * Mapper for Commune
 */
@Component
public class CommuneMapperImpl implements CommuneMapper {


    @Override
    public Commune toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Commune commune = new Commune();
        commune.setNomSimple(dto.getNomCommuneSimple());
        commune.setNomReel(dto.getNomCommuneReel());
        commune.setCodePostal(dto.getCodePostal());
        commune.setCodeInsee(dto.getCodeCommuneINSEE());
        return commune;
    }

    @Override
    public InfoCarteCommune toDto(Commune commune) {
        Coordonnee coordonnee = commune.getCoordonnee();
        Set<Mesure> mesures = coordonnee.getMesures();

        List<MesureAir> latestAirs = getMesureAir(mesures);
        List<MesurePrevision> latestPrevisions = getMesurePrevision(mesures);

        InfoCarteCommune carte = new InfoCarteCommune();
        carte.setId(commune.getId());
        carte.setCodeInsee(commune.getCodeInsee());
        carte.setLatitude(coordonnee.getLatitude());
        carte.setLongitude(coordonnee.getLongitude());
        carte.setNomVille(commune.getNomSimple());

        InfoCarteCommuneDetailQualiteAir detailAir = new InfoCarteCommuneDetailQualiteAir();
        setDetailAir(latestAirs, detailAir);

        InfoCarteCommuneDetailMeteo detailMeteo = new InfoCarteCommuneDetailMeteo();
        setDetailMeteo(latestPrevisions, detailMeteo);

        carte.setDetailMeteo(detailMeteo);
        carte.setDetailQualiteAir(detailAir);

        extrapolateDescriptionMeteo(carte);
        return carte;
    }


    @Override
    public InfoFavorite toDto(Commune commune, Long userId) {
        InfoFavorite fav = new InfoFavorite();
        fav.setFavID(userId, commune.getId());
        fav.setInformations(toDto(commune));
        return fav;
    }

    /**
     * <p>Cette methode déduit à partir des mesures mappées dans {@link InfoCarteCommuneDetailMeteo} une description du temps.</p>
     * <p>Afin de permettre au front d'associer une image liée aux qualificatifs de {@link NatureMesurePrevision}</p>
     * <p>La valeur est ensuite associée au DTO</p>
     *
     * @param carte le dto envoyé
     */
    private void extrapolateDescriptionMeteo(InfoCarteCommune carte) {
        InfoCarteCommuneDetailMeteo detailMeteo = carte.getDetailMeteo();
        if (detailMeteo == null || detailMeteo.getMeteo().isEmpty()) {
            return;
        }

        Map<NatureMesurePrevision, Map<Double, String>> meteo = detailMeteo.getMeteo();

        double rain = getMaxValue(meteo, NatureMesurePrevision.RAIN_1H, NatureMesurePrevision.RAIN_3H);
        double snow = getMaxValue(meteo, NatureMesurePrevision.SNOW_1H, NatureMesurePrevision.SNOW_3H);
        double cloud = getMaxValue(meteo, NatureMesurePrevision.CLOUD_COVERAGE);
        double gust = getMaxValue(meteo, NatureMesurePrevision.WIND_SPEED_GUST);

        DescriptionMeteo description;

        if (snow > 0.1) {
            description = DescriptionMeteo.NEIGE;
        } else if (rain > 0.1) {
            description = DescriptionMeteo.PLUIE;
        } else if (gust > 50.0) {
            description = DescriptionMeteo.ORAGE;
        } else if (cloud > 90.0) {
            description = DescriptionMeteo.COUVERT;
        } else if (cloud > 40.0) {
            description = DescriptionMeteo.SOLEIL_NUAGE;
        } else {
            description = DescriptionMeteo.SOLEIL;
        }

        carte.setDescriptionMeteo(description);
    }

    /**
     * Récupère la valeur la plus haute mappée dans {@link InfoCarteCommuneDetailMeteo} pour le mot clef {@link NatureMesurePrevision}
     *
     * @param meteo le composant {@link InfoCarteCommuneDetailMeteo}
     * @param types le type {@link NatureMesurePrevision}
     * @return la valeur maximale associée au type
     */
    private double getMaxValue(Map<NatureMesurePrevision, Map<Double, String>> meteo, NatureMesurePrevision... types) {
        return Arrays.stream(types)
                .filter(meteo::containsKey)
                .flatMap(type -> meteo.get(type).keySet().stream())
                .max(Double::compareTo)
                .orElse(0.0);
    }
}
