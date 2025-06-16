package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.utils.api.HttpRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private HttpRequestUtils httpRequestUtils;

    /**
     * Connexion d'un utilisateur déjà inscrit
     *
     * @param userDto utilisateur à l'origine de la tentative de connexion
     * @return message de succès (et création de cookie) si connexion réussie
     * @throws Exception si la connexion échoue
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UtilisateurDto userDto) throws Exception {
            ResponseCookie cookie = authService.logUser(userDto);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Vous êtes connecté");
    }

    /**
     * Création d'un nouvel utilisateur
     *
     * @param userDto utilisateur dans le corps de la requête
     * @return message de confirmation si l'inscription réussit
     * @throws Exception si des erreurs métier sont rencontrées
     */
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UtilisateurDto userDto) throws Exception {
        authService.createUser(userDto, RoleUtilisateur.UTILISATEUR);
        return ResponseEntity.ok("Utilisateur créé");
    }
}
