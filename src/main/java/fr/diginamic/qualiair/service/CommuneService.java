package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.CommuneDto;
import fr.diginamic.qualiair.dto.carte.FiveDaysForecastView;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.RouteParamException;

import java.util.List;

public interface CommuneService {
    /**
     * Met à jour une commune à partir de son nom.
     *
     * @param commune la commune à mettre à jour
     */
    void updateByName(Commune commune);

    /**
     * Cherche une commune dans le cache ou la crée et l'ajoute au cache.
     *
     * @param commune la commune à chercher ou créer
     * @return la commune existante ou nouvellement créée
     */
    Commune findOrCreate(Commune commune);

    /**
     * Récupère une commune dans le cache à partir de son nom.
     *
     * @param communeName nom de la commune
     * @return la commune trouvée dans le cache, ou null si absente
     */
    Commune getFromCache(String communeName);

    /**
     * Récupère une liste DTO des communes avec une population minimale donnée.
     *
     * @param nbHabitant nombre d'habitants minimum
     * @return liste des DTO {@link InfoCarteCommune}
     */
    List<InfoCarteCommune> getListCommunesDtoByPopulation(int nbHabitant);

    /**
     * Récupère une liste des communes avec la plus récente mesure de population,
     * filtrée par population minimale.
     *
     * @param nbHabitant nombre d'habitants minimum
     * @return liste des communes
     */
    List<Commune> getListTopCommunesByPopulation(int nbHabitant);

    /**
     * Récupère toutes les communes qui ont des coordonnées.
     *
     * @return liste des communes
     */
    List<Commune> getAllCommunesWithCoordinates();

    /**
     * Recherche jusqu'à 10 communes dont le nom contient la chaîne donnée.
     *
     * @param containing chaîne à chercher (min. 3 caractères)
     * @return liste des DTO {@link CommuneDto}
     * @throws RouteParamException si la chaîne est trop courte
     */
    List<CommuneDto> matchTop10ByName(String containing) throws RouteParamException;

    /**
     * Récupère toutes les communes favorites d'un utilisateur.
     *
     * @param userId identifiant de l'utilisateur
     * @return liste des communes favorites
     */
    List<Commune> getAllFavoritesByUserId(Long userId);

    /**
     * Récupère une commune par son identifiant.
     *
     * @param communeId identifiant de la commune
     * @return la commune trouvée
     * @throws DataNotFoundException si aucune commune ne correspond à l'id
     */
    Commune getCommuneById(Long communeId) throws DataNotFoundException;

    InfoCarteCommune getCommuneDtoByCodeInsee(String codeInsee) throws DataNotFoundException;

    FiveDaysForecastView getCommuneForecastByCodeInsee(String codeInsee) throws DataNotFoundException;

}
