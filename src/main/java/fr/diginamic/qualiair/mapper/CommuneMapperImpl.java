package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommuneDetailMeteo;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommuneDetailQualiteAir;
import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
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
        return carte;
    }


    @Override
    public InfoFavorite toDto(Commune commune, Long userId) {
        InfoFavorite fav = new InfoFavorite();
        fav.setFavID(userId, commune.getId());
        fav.setInformations(toDto(commune));
        return fav;
    }
}
