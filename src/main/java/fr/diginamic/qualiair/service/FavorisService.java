package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.UpdateException;
import fr.diginamic.qualiair.security.CusomUserPrincipal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavorisService {
    /**
     * Récupère toutes les {@link Commune} favorites de {@link Utilisateur}.
     *
     * @param user utilisateur courant
     * @return liste des {@link InfoFavorite} correspondant aux {@link Commune} favorites
     */
    List<InfoFavorite> getAllUserFavorites(CusomUserPrincipal user);

    /**
     * Ajoute une commune dans les favoris de {@link Utilisateur}.
     *
     * @param user      utilisateur courant
     * @param communeId id de la commune à ajouter
     * @return liste mise à jour des favoris au format {@link InfoFavorite}
     * @throws DataNotFoundException si {@link Utilisateur} ou la commune n'existe pas
     * @throws UpdateException       si la commune est déjà dans les favoris
     * @throws BusinessRuleException en cas de violation métier
     */
    @Transactional
    List<InfoFavorite> addNewUserFavorite(CusomUserPrincipal user, Long communeId) throws DataNotFoundException, UpdateException, BusinessRuleException;

    /**
     * Supprime une commune des favoris de l'utilisateur.
     *
     * @param user      utilisateur courant
     * @param communeId id de la commune à retirer
     * @return liste mise à jour des favoris au format {@link InfoFavorite}
     * @throws DataNotFoundException si la commune n'est pas dans les favoris
     * @throws BusinessRuleException en cas de violation métier
     */
    @Transactional
    List<InfoFavorite> removeUserFavorite(CusomUserPrincipal user, Long communeId) throws DataNotFoundException, BusinessRuleException;
}
