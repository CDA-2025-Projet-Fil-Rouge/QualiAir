package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthController {
    /**
     * Connexion d'un utilisateur déjà inscrit
     *
     * @param userDto utilisateur à l'origine de la tentative de connexion
     * @return message de succès (et création de cookie) si connexion réussie
     */
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UtilisateurDto userDto);

    /**
     * Création d'un nouvel utilisateur
     *
     * @param userDto utilisateur dans le corps de la requête
     * @return message de confirmation si l'inscription réussit
     * @throws Exception si des erreurs métier sont rencontrées
     */
    @PostMapping("/register")
    ResponseEntity<String> createUser(@RequestBody UtilisateurDto userDto) throws Exception;

    /**
     * Déconnexion d'un utilisateur connecté
     *
     * @return message de confirmation
     */
    @PostMapping("/logout")
    ResponseEntity<String> logout();
}
