package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.security.JwtAuthentificationService;
import fr.diginamic.qualiair.service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe utilitaire pour extraire des informations utiles de la requête HTTP.
 */
@Component
public class HttpRequestUtilsImpl implements HttpRequestUtils {

    @Autowired
    private JwtAuthentificationService jwtAuthentificationService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    public Utilisateur getUtilisateurFromRequest(HttpServletRequest request) throws Exception {
        String email = jwtAuthentificationService.getEmailFromCookie(request);
        return utilisateurService.getUser(email);
    }
}
