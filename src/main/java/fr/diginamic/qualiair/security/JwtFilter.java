package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Filtre de sécurité exécuté à chaque requête HTTP pour vérifier l'authentification via un token JWT.
 */
@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    IJwtAuthentificationService IJwtAuthentificationService;

    @Value("${jwt.cookie}")
    private String TOKEN_COOKIE;

    @Autowired
    private UtilisateurService utilisateurService;

    /**
     * Filtre HTTP exécuté une fois par requête. Vérifie la présence d'un token JWT dans les cookies,
     * authentifie l'utilisateur et configure le contexte de sécurité si le token est valide.
     *
     * @param req la requête HTTP
     * @param response la réponse HTTP
     * @param filterChain la chaîne de filtres à poursuivre
     * @throws ServletException si une erreur de filtre survient
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (req.getCookies() != null) {
            Stream.of(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(TOKEN_COOKIE))
                    .map(Cookie::getValue)
                    .forEach(token -> {
                        try {
                            processToken(token, response);
                        } catch (IOException e) {
                            throw new RuntimeException("Erreur d'écriture dans la réponse HTTP", e);
                        }
                    });
        }

        filterChain.doFilter(req, response);
    }

    /**
     * Traite un token JWT reçu dans un cookie, effectue les vérifications de validité,
     * charge l'utilisateur associé et configure le contexte de sécurité Spring.
     * Si le token est invalide ou si l'utilisateur est banni, la méthode renvoie immédiatement
     * une réponse d'erreur via l'objet HttpServletResponse.
     *
     * @param token le token JWT à valider
     * @param response la réponse HTTP dans laquelle une erreur peut être renvoyée
     * @throws IOException si une erreur survient lors de l'envoi d'une réponse d'erreur
     */
    private void processToken(String token, HttpServletResponse response) throws IOException {
        if (!IJwtAuthentificationService.validateToken(token)) {
            return;
        }
        String email = IJwtAuthentificationService.getSubject(token);

        try {
            Utilisateur user = utilisateurService.getUser(email);

            if (user.getRole() == RoleUtilisateur.BANNI) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé : utilisateur banni.");
                return;
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (AccessDeniedException e) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Échec d'authentification.");
        }
    }
}