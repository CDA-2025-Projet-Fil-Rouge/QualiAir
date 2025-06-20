package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.UpdateException;
import fr.diginamic.qualiair.security.CusomUserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interface du contrôleur REST pour gérer les favoris d'un utilisateur.
 * Permet de récupérer, ajouter et supprimer des favoris associés à un utilisateur authentifié.
 */
public interface FavorisController {
    /**
     * Récupère la liste de tous les favoris de l'utilisateur connecté.
     *
     * @param user L'utilisateur authentifié via Spring Security.
     * @return Une réponse HTTP contenant la liste des favoris sous forme de {@link InfoFavorite}.
     */
    @GetMapping("/get-all")
    ResponseEntity<List<InfoFavorite>> getAllFavoris(@AuthenticationPrincipal CusomUserPrincipal user);

    /**
     * Ajoute une nouvelle commune aux favoris de l'utilisateur connecté.
     *
     * @param user      L'utilisateur authentifié via Spring Security.
     * @param communeId L'identifiant de la commune à ajouter aux favoris.
     * @return Une réponse HTTP contenant la liste mise à jour des favoris.
     * @throws DataNotFoundException Si la commune avec l'ID donné n'existe pas.
     * @throws BusinessRuleException Si une règle métier empêche l'ajout du favori.
     * @throws UpdateException       Si une erreur survient lors de la mise à jour des favoris.
     */
    @PostMapping("/add")
    ResponseEntity<List<InfoFavorite>> addNew(@AuthenticationPrincipal CusomUserPrincipal user, @RequestBody Long communeId) throws DataNotFoundException, BusinessRuleException, UpdateException;

    /**
     * Supprime une commune des favoris de l'utilisateur connecté.
     *
     * @param user      L'utilisateur authentifié via Spring Security.
     * @param communeId L'identifiant de la commune à retirer des favoris.
     * @return Une réponse HTTP contenant la liste mise à jour des favoris.
     * @throws DataNotFoundException Si la commune avec l'ID donné n'existe pas dans les favoris.
     * @throws BusinessRuleException Si une règle métier empêche la suppression du favori.
     */
    @DeleteMapping("delete/{communeId}")
    ResponseEntity<List<InfoFavorite>> removeById(@AuthenticationPrincipal CusomUserPrincipal user, @PathVariable Long communeId) throws DataNotFoundException, BusinessRuleException;
}
