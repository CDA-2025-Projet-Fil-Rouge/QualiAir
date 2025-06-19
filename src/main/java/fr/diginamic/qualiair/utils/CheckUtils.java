package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;

public final class CheckUtils {

    /**
     * Vérifie que deux identifiants correspondent.
     *
     * @param entityId l'identifiant de l'entité (souvent extrait de l'URL).
     * @param dtoId    l'identifiant provenant du corps de la requête (DTO).
     * @throws IllegalArgumentException si les deux identifiants ne sont pas égaux.
     */
    public static void ensureMatchingIds(Long entityId, Long dtoId) {
        if (!entityId.equals(dtoId)) {
            throw new IllegalArgumentException("L'identifiant de l'URL et celui du corps ne correspondent pas.");
        }
    }

    /**
     * Recherche en base si un email existe déjà pour garantir son unicité
     * @param repo UtilisateurRepository à utiliser pour la recherche
     * @param email email à vérifier
     * @throws BusinessRuleException si l'email existe déjà
     */
    public static void ensureUniqueEmail(UtilisateurRepository repo, String email)
    throws BusinessRuleException {
        if (repo.existsByEmail(email)) {
            throw new BusinessRuleException("Cet email est déjà utilisé par un autre utilisateur");
        }
    }
}
