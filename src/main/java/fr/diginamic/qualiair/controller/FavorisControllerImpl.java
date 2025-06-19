package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.UpdateException;
import fr.diginamic.qualiair.security.CusomUserPrincipal;
import fr.diginamic.qualiair.service.FavorisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les favoris d'un utilisateur.
 * Permet de récupérer, ajouter et supprimer des favoris associés à un utilisateur authentifié.
 */
@RestController
@RequestMapping(path = "/favoris")
public class FavorisControllerImpl implements FavorisController {

    @Autowired
    private FavorisService service;

    @GetMapping("/get-all")
    @Override
    public ResponseEntity<List<InfoFavorite>> getAllFavoris(@AuthenticationPrincipal CusomUserPrincipal user) {
        return ResponseEntity.ok().body(service.getAllUserFavorites(user));
    }

    @PostMapping("/add")
    @Override
    public ResponseEntity<List<InfoFavorite>> addNew(@AuthenticationPrincipal CusomUserPrincipal user, @RequestBody Long communeId) throws DataNotFoundException, BusinessRuleException, UpdateException {
        return ResponseEntity.ok().body(service.addNewUserFavorite(user, communeId));
    }

    @DeleteMapping("delete/{communeId}")
    @Override
    public ResponseEntity<List<InfoFavorite>> removeById(@AuthenticationPrincipal CusomUserPrincipal user, @PathVariable Long communeId) throws DataNotFoundException, BusinessRuleException {
        return ResponseEntity.ok().body(service.removeUserFavorite(user, communeId));
    }

}
