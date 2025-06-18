package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.service.UtilisateurService;
import fr.diginamic.qualiair.utils.api.HttpRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     * Récupère les informations personnelles de l'utilisateur connecté.
     *
     * @param request la requête HTTP contenant le token JWT
     * @return les données personnelles de l'utilisateur connecté
     * @throws Exception si l'utilisateur n'est pas authentifié
     */
    @GetMapping("/get-personal-data")
    public ResponseEntity<UtilisateurDto> getPersonalData(HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        UtilisateurDto dto = utilisateurService.getPersonalData(user.getId());
        return ResponseEntity.ok(dto);
    }

    /**
     * Retourne la liste paginée de tous les utilisateurs inscrits,
     * Accessible uniquement par un administrateur ou super administrateur.
     *
     * @param page numéro de la page (défaut : 0)
     * @param size taille de la page (défaut : 15)
     * @param request requête HTTP contenant le JWT
     * @return page de UtilisateurDto
     * @throws Exception en cas d’accès interdit ou erreur interne
     */
    @GetMapping("/get-all")
    public ResponseEntity<Page<UtilisateurDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        Pageable pageable = PageRequest.of(page, size, Sort.by("nom").ascending());
        return ResponseEntity.ok(utilisateurService.getAllUsers(pageable, demandeur));
    }

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
     * Permet à un utilisateur connecté de modifier ses informations personnelles
     * @param dto données utilisateur à modifier dans le corps de la requête
     * @param request la requête HTTP contenant le token JWT
     * @return les données personnelles de l'utilisateur connecté
     * @throws Exception si l'utilisateur n'est pas authentifié
     */
    @PutMapping("/update-personal-data")
    public ResponseEntity<UtilisateurUpdateDto> updatePersonalData(
            @RequestBody UtilisateurUpdateDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur utilisateur = httpRequestUtils.getUtilisateurFromRequest(request);
        UtilisateurUpdateDto updated = utilisateurService.updatePersonalData(utilisateur, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Permet à un utilisateur connecté de modifier son adresse personnelle
     * @param dto instance de AdresseDto correspondant à la nouvelle adresse
     * @param request la requête HTTP contenant le token JWT
     * @return la dto correspondant à l'utilisateur modifié
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-adresse")
    public ResponseEntity<AdresseDto> updateAdresse(
            @RequestBody AdresseDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur utilisateur = httpRequestUtils.getUtilisateurFromRequest(request);
        AdresseDto updated = utilisateurService.updateUserAdresse(utilisateur, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Changement de rôle en cas de désactivation ou réactivation d'un utilisateur
     * (autorisé uniquement à un admin ou superadmin)
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
        String message = utilisateurService.toggleActivationUser(idUser, demandeur);
        return ResponseEntity.ok(message);
    }

    /**
     * Changement de rôle en cas de bannissement ou débannissement d'un utilisateur
     * (autorisé uniquement à un admin ou superadmin)
     * @param idUser identifiant de l'utilisateur à bannir/débannir
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return message de confirmation si le changement a réussi
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/toggle-ban/{id}")
    public ResponseEntity<String> toggleBanUser(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception {
        Utilisateur demandeur = httpRequestUtils.getUtilisateurFromRequest(request);
        String message = utilisateurService.toggleBanUser(idUser, demandeur);
        return ResponseEntity.ok(message);
    }
}
