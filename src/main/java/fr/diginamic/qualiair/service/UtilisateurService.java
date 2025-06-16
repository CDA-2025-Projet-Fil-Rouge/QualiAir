package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.security.IJwtAuthentificationService;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    BCryptPasswordEncoder bcrypt;
    @Autowired
    IJwtAuthentificationService IJwtAuthentificationService;
    @Autowired
    private UtilisateurValidator utilisateurValidator;


    /**
     * Récupère un utilisateur à partir de son email.
     *
     * @param email Email de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé
     */
    public Utilisateur getUser(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    /**
     * Authentifie un utilisateur en comparant le mot de passe, puis génère un token JWT.
     *
     * @param user L'utilisateur à authentifier
     * @return un cookie contenant le token JWT
     * @throws Exception si les identifiants sont invalides
     */
    public ResponseCookie logUser(Utilisateur user) throws Exception {
        Utilisateur utilisateur = getUser(user.getEmail());
        if (bcrypt.matches(user.getMotDePasse(), utilisateur.getMotDePasse())) {
            return IJwtAuthentificationService.generateToken(utilisateur);
        }
        throw new BadCredentialsException("Email ou mot de passe incorrect");
    }

    /**
     * Crée un nouvel utilisateur après validation des règles métier.
     *
     * @param user Utilisateur à créer (non encore persisté)
     * @param role Rôle à affecter au nouvel utilisateur
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     */
    public void createUser(Utilisateur user, RoleUtilisateur role)
            throws BusinessRuleException, TokenExpiredException {
        utilisateurValidator.validate(user);

        Utilisateur userToSave = new Utilisateur();
        userToSave.setEmail(user.getEmail());
        userToSave.setMotDePasse(bcrypt.encode(user.getMotDePasse()));
        userToSave.setPrenom(user.getPrenom());
        userToSave.setNom(user.getNom());
        Optional.ofNullable(user.getDateNaissance()).ifPresent(userToSave::setDateNaissance);
        userToSave.setAdresse(user.getAdresse());
        userToSave.setDateInscription(LocalDateTime.now());
        userToSave.setRole(role);

        utilisateurRepository.save(userToSave);
    }
}
