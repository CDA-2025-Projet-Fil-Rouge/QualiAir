package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface UtilisateurController {
    /**
     * Récupère les informations personnelles de l'utilisateur connecté.
     *
     * @param request la requête HTTP contenant le token JWT
     * @return les données personnelles de l'utilisateur connecté
     * @throws Exception si l'utilisateur n'est pas authentifié
     */
    @GetMapping("/get-personal-data")
    ResponseEntity<UtilisateurDto> getPersonalData(HttpServletRequest request) throws Exception;

    /**
     * Retourne la liste paginée de tous les utilisateurs inscrits,
     * Accessible uniquement par un administrateur ou super administrateur.
     *
     * @param page    numéro de la page (défaut : 0)
     * @param size    taille de la page (défaut : 15)
     * @param request requête HTTP contenant le JWT
     * @return page de UtilisateurDto
     * @throws Exception en cas d’accès interdit ou erreur interne
     */
    @GetMapping("/get-all")
    ResponseEntity<Page<UtilisateurDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            HttpServletRequest request) throws Exception;

    /**
     * Permet à un utilisateur connecté de modifier ses informations personnelles
     *
     * @param dto     données utilisateur à modifier dans le corps de la requête
     * @param request la requête HTTP contenant le token JWT
     * @return les données personnelles de l'utilisateur connecté
     * @throws Exception si l'utilisateur n'est pas authentifié
     */
    @PutMapping("/update-personal-data")
    ResponseEntity<UtilisateurUpdateDto> updatePersonalData(
            @RequestBody UtilisateurUpdateDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Changement de rôle en cas de désactivation ou réactivation d'un utilisateur
     * (autorisé uniquement à un admin ou superadmin)
     *
     * @param idUser  identifiant de l'utilisateur à activer/désactiver
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return message de confirmation si le changement a réussi
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PostMapping("/toggle-admin/{id}")
    ResponseEntity<String> toggleAdminRole(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception;

    /**
     * Changement de rôle en cas de désactivation ou réactivation d'un utilisateur
     * (autorisé uniquement à un admin ou superadmin)
     *
     * @param idUser  identifiant de l'utilisateur à activer/désactiver
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return message de confirmation si le changement a réussi
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/toggle-activation/{id}")
    ResponseEntity<String> toggleActivationRole(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception;

    /**
     * Changement de rôle en cas de bannissement ou débannissement d'un utilisateur
     * (autorisé uniquement à un admin ou superadmin)
     *
     * @param idUser  identifiant de l'utilisateur à bannir/débannir
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return message de confirmation si le changement a réussi
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/toggle-ban/{id}")
    ResponseEntity<String> toggleBanUser(
            @PathVariable("id") Long idUser,
            HttpServletRequest request) throws Exception;
}
