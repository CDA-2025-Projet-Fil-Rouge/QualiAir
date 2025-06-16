package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.AdresseRepository;
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
    public static boolean isAdmin(Utilisateur user)  {
        if (user.getRole() == RoleUtilisateur.ADMIN || user.getRole() == RoleUtilisateur.SUPERADMIN) {
            return true;
        }
        throw new AccessDeniedException("Fonction réservée aux administrateurs.");
    }

    /**
     * Méthode statique pour vérifier si un utilisateur est superadmin
     * @param user désigne l'utilisateur à vérifier
     * @return true si superadmin, sinon AccessDeniedException
     */
    public static boolean isSuperadmin(Utilisateur user) {
        if(user.getRole().equals(RoleUtilisateur.SUPERADMIN)) {
            return true;
        } else {
            throw new AccessDeniedException("Cette fonction est réservée aux superadmins");
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

    /**
     * Récupère une adresse par son identifiant ou déclenche une exception si elle n'existe pas.
     *
     * @param id identifiant de l'adresse
     * @return l'adresse trouvée
     * @throws FileNotFoundException si aucune adresse n'est trouvée
     */
    public static Adresse findAdresseOrThrow(AdresseRepository repo, Long id) throws FileNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Adresse introuvable"));
    }
}
