package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
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
public class FavorisServiceImpl implements FavorisService {
    @Autowired
    private CommuneService communeService;
    @Autowired
    private CommuneMapper mapper;
    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public List<InfoFavorite> getAllUserFavorites(CusomUserPrincipal user) {
        Long userId = user.getId();
        List<Commune> communes = communeService.getAllFavoritesByUserId(userId);
        return communes.stream().map(c -> mapper.toDto(c, userId)).toList();
    }

    @Transactional
    @Override
    public List<InfoFavorite> addNewUserFavorite(CusomUserPrincipal user, Long communeId) throws DataNotFoundException, UpdateException, BusinessRuleException {
        Utilisateur utilisateur = utilisateurService.getUserById(user.getId());
        Commune commune = communeService.getCommuneById(communeId);
        if (utilisateur.getFavCommunes().contains(commune)) {
            throw new UpdateException("Commune déjà présente dans les favoris de l'utilisateur: " + commune.getNomSimple());
        }
        utilisateur.getFavCommunes().add(commune);
        utilisateurService.updateUser(utilisateur);

        return utilisateur.getFavCommunes().stream().map(c -> mapper.toDto(c, user.getId())).toList();
    }

    @Transactional
    @Override
    public List<InfoFavorite> removeUserFavorite(CusomUserPrincipal user, Long communeId) throws DataNotFoundException, BusinessRuleException {
        Utilisateur utilisateur = utilisateurService.getUserById(user.getId());
        Commune commune = communeService.getCommuneById(communeId);
        if (!utilisateur.getFavCommunes().contains(commune)) {
            throw new DataNotFoundException("Cette commune ne fait pas partie des favoris de l'utilisateur: " + utilisateur.getEmail());
        }
        utilisateur.getFavCommunes().remove(commune);
        utilisateurService.updateUser(utilisateur);
        return utilisateur.getFavCommunes().stream().map(c -> mapper.toDto(c, user.getId())).toList();
    }
}
