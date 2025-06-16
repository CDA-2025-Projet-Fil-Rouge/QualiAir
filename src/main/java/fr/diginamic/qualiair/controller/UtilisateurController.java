package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.service.UtilisateurService;
import fr.diginamic.qualiair.utils.api.HttpRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UtilisateurController {

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    HttpRequestUtils httpRequestUtils;

    /**
     * Création d'un nouvel admin (autorisée uniquement à un utilisateur de type superadmin)
     *
     * @param userDto utilisateur dans le corps de la requête
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return message de confirmation si l'inscription réussit
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin(
            @RequestBody UtilisateurDto userDto,
            HttpServletRequest request) throws Exception {

        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        utilisateurService.createAdmin(userDto, demandeur, RoleUtilisateur.ADMIN);
        return ResponseEntity.ok("Admin créé");
    }

    /**
     * Changement de rôle en cas de désactivation ou réactivation d'un utilisateur
     * (autorisée uniquement à un admin ou superadmin)
     * @param idUser identifiant de l'utilisateur à activer/désactiver
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return message de confirmation si le changement a réussi
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/toggle-role/{id}")
    public ResponseEntity<String> toggleUserRole(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        String message = utilisateurService.toggleRole(idUser, demandeur);
        return ResponseEntity.ok(message);
    }
}
