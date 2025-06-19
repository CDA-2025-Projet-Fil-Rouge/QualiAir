package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.InfoFavorite;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.UpdateException;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.security.CusomUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service gérant les favoris d'un {@link Utilisateur}, c'est-à-dire la liste des {@link Commune} favorites.
 * <p>
 * Permet de récupérer, ajouter ou supprimer des {@link Commune} favorites pour un {@link Utilisateur} donné.
 */
@Service
public class FavorisService {
    @Autowired
    private CommuneService communeService;
    @Autowired
    private CommuneMapper mapper;
    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * Récupère toutes les {@link Commune} favorites de {@link Utilisateur}.
     *
     * @param user utilisateur courant
     * @return liste des {@link InfoFavorite} correspondant aux {@link Commune} favorites
     */
    public List<InfoFavorite> getAllUserFavorites(CusomUserPrincipal user) {
        Long userId = user.getId();
        List<Commune> communes = communeService.getAllFavoritesByUserId(userId);
        return communes.stream().map(c -> mapper.toDto(c, userId)).toList();
    }

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
    public List<InfoFavorite> addNewUserFavorite(CusomUserPrincipal user, Long communeId) throws DataNotFoundException, UpdateException, BusinessRuleException {
        Utilisateur utilisateur = utilisateurService.getUserById(user.getId());
        Commune commune = communeService.getCommuneById(communeId);
        if (utilisateur.getFavCommunes().contains(commune)) {
            throw new UpdateException("Commune déjà présente dans les favoris de l'utilisateur: " + commune.getNomSimple());
        }
        utilisateur.getFavCommunes().add(commune);
        utilisateurService.saveUser(utilisateur);

        return utilisateur.getFavCommunes().stream().map(c -> mapper.toDto(c, user.getId())).toList();
    }

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
    public List<InfoFavorite> removeUserFavorite(CusomUserPrincipal user, Long communeId) throws DataNotFoundException, BusinessRuleException {
        Utilisateur utilisateur = utilisateurService.getUserById(user.getId());
        Commune commune = communeService.getCommuneById(communeId);
        if (!utilisateur.getFavCommunes().contains(commune)) {
            throw new DataNotFoundException("Cette commune ne fait pas partie des favoris de l'utilisateur: " + utilisateur.getEmail());
        }
        utilisateur.getFavCommunes().remove(commune);
        utilisateurService.saveUser(utilisateur);
        return utilisateur.getFavCommunes().stream().map(c -> mapper.toDto(c, user.getId())).toList();
    }
}
