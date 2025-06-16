package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import org.springframework.security.access.AccessDeniedException;

/**
 * Classe utilitaire de vérification de l'entité Utilisateur
 */
public final class UtilisateurUtils {

    private UtilisateurUtils() {}

    /**
     * Méthode statique pour vérifier si un utilisateur est admin
     * @param user désigne l'utilisateur à vérifier
     * @return true si admin, sinon AccessDeniedException
     */
    public static boolean isAdmin(Utilisateur user) {
        if(user.getRole().equals(RoleUtilisateur.ADMIN)) {
            return true;
        } else {
            throw new AccessDeniedException("Cette fonction est réservée aux admins");
        }
    }

    /**
     * Méthode utilitaire dédiée au forum, lorsque l'autorisation est réservée à l'auteur du message ou à un admin
     * @param user désigne l'utilisateur à vérifier
     * @param authorId désigne l'id de l'auteur du message à comparer
     * Jète une AccessDeniedException si l'utilisateur ne remplit pas les conditions.
     */
    public static void checkAuthorOrAdmin(Utilisateur user, Long authorId) {
        if (!user.getId().equals(authorId) && !isAdmin(user)) {
            throw new AccessDeniedException("Seul un administrateur ou l'auteur peut modifier.");
        }
    }

}
