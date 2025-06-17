package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.service.UtilisateurService;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Classe de service dédiée à l'inscription/connexion sécurisée des utilisateurs
 */
@Service
public class AuthService {

    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    BCryptPasswordEncoder bcrypt;
    @Autowired
    IJwtAuthentificationService IJwtAuthentificationService;
    @Autowired
    AdresseRepository adresseRepository;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurMapper utilisateurMapper;
    @Autowired
    UtilisateurValidator utilisateurValidator;

    /**
     * Authentifie un utilisateur en comparant le mot de passe, puis génère un token JWT.
     *
     * @param userDto L'utilisateur à authentifier
     * @return un cookie contenant le token JWT
     * @throws Exception si les identifiants sont invalides
     */
    public ResponseCookie logUser(UtilisateurDto userDto) throws Exception {
        Utilisateur utilisateur = utilisateurService.getUser(userDto.getEmail());
        if (bcrypt.matches(userDto.getMotDePasse(), utilisateur.getMotDePasse())) {
            return IJwtAuthentificationService.generateToken(utilisateur);
        }
        throw new BadCredentialsException("Email ou mot de passe incorrect");
    }

    /**
     * Crée un nouvel utilisateur après validation des règles métier.
     *
     * @param userDto Utilisateur à créer (non encore persisté)
     * @param role Rôle à affecter au nouvel utilisateur
     * @throws FileNotFoundException si l'adresse associée à l'utilisateur n'est pas trouvée
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     */
    public void createUser(UtilisateurDto userDto, RoleUtilisateur role)
            throws FileNotFoundException, BusinessRuleException, TokenExpiredException {

        Adresse adresse = UtilisateurUtils.findAdresseOrThrow(adresseRepository, userDto.getIdAdresse());
        Utilisateur userToSave = utilisateurMapper.fromDto(userDto, adresse, role);
        utilisateurValidator.validate(userToSave);
        utilisateurRepository.save(userToSave);
    }
}
