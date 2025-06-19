package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller : gestion de l'inscription,
 * de la connexion et de la déconnexion d'un utilisateur
 */
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    @Autowired
    private AuthService authService;
    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @PostMapping("/login")
    @Override
    public ResponseEntity<String> login(@RequestBody UtilisateurDto userDto) {
        ResponseCookie cookie = authService.logUser(userDto);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Vous êtes connecté");
    }

    @PostMapping("/register")
    @Override
    public ResponseEntity<String> createUser(@RequestBody UtilisateurDto userDto) throws Exception {

        authService.createUser(userDto, RoleUtilisateur.UTILISATEUR);
        return ResponseEntity.ok("Utilisateur créé");
    }

    @PostMapping("/logout")
    @Override
    public ResponseEntity<String> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from(TOKEN_COOKIE, "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body("Déconnexion réussie");
    }
}
