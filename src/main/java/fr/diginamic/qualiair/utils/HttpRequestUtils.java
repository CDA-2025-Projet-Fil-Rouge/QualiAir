package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;

public interface HttpRequestUtils {
    /**
     * Récupère l'utilisateur à partir du JWT contenu dans les cookies de la requête.
     *
     * @param request requête HTTP
     * @return utilisateur authentifié
     * @throws Exception en cas de JWT invalide ou d'utilisateur introuvable
     */
    Utilisateur getUtilisateurFromRequest(HttpServletRequest request) throws Exception;
}
