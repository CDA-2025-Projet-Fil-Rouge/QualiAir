package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import org.springframework.http.ResponseCookie;

public interface AuthService {
    /**
     * Authentifie un utilisateur en comparant le mot de passe, puis génère un token JWT.
     *
     * @param userDto L'utilisateur à authentifier
     * @return un cookie contenant le token JWT
     */
    ResponseCookie logUser(UtilisateurDto userDto);

    /**
     * Crée un nouvel utilisateur après validation des règles métier.
     *
     * @param userDto Utilisateur à créer (non encore persisté)
     * @param role    Rôle à affecter au nouvel utilisateur
     * @throws FileNotFoundException si l'adresse associée à l'utilisateur n'est pas trouvée
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     */
    void createUser(UtilisateurDto userDto, RoleUtilisateur role)
            throws FileNotFoundException, BusinessRuleException;
}
