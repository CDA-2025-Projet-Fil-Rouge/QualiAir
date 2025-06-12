package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.entity.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

/**
 * Service responsable de la génération et de la validation des JWT.
 */
public interface IJwtAuthentificationService {

    /**
     * Génère un token JWT pour l'utilisateur donné.
     * @param user utilisateur à authentifier
     * @return cookie HTTP contenant le JWT
     */
    ResponseCookie generateToken(Utilisateur user);

    /**
     * Extrait le sujet du token JWT.
     * @param token le JWT
     * @return l'email de l'utilisateur
     */
    String getSubject(String token);

    /**
     * Récupère l'email de l'utilisateur à partir du cookie JWT dans la requête HTTP.
     * @param request requête HTTP contenant les cookies
     * @return l'email extrait du JWT
     * @throws Exception si aucun token valide n'est trouvé
     */
    String getEmailFromCookie(HttpServletRequest request) throws Exception;

    /**
     * Vérifie la validité du token JWT.
     * @param token le JWT
     * @return true si le token est valide, false sinon
     */
    Boolean validateToken(String token);
}
