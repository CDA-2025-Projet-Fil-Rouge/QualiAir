package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private RoleManagementService roleManagementService;
    @Autowired
    private UtilisateurValidator utilisateurValidator;
    @Autowired
    private AdresseRepository adresseRepository;

    /**
     * Récupérer un utilisateur à partir de son email.
     *
     * @param email Email de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé
     */
    public Utilisateur getUser(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    public Utilisateur getUserById(Long id) throws DataNotFoundException {
        return utilisateurRepository.findById(id).orElseThrow((() -> new DataNotFoundException("Utilisateur non trouvé pour l'id: " + id)));
    }

    public Utilisateur saveUser(Utilisateur utilisateur) throws BusinessRuleException {
        utilisateurValidator.validate(utilisateur);
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Récupérer la liste paginée de tous les utilisateurs inscrits.
     * Accessible uniquement aux administrateurs et super-administrateurs.
     *
     * @param pageable  objet de pagination contenant le numéro de page, la taille de page, et éventuellement le tri
     * @param demandeur utilisateur authentifié à l'origine de la requête
     * @return une page de UtilisateurDto représentant les utilisateurs
     * @throws AccessDeniedException si l'utilisateur connecté n'a pas les droits suffisants
     */
    public Page<UtilisateurDto> getAllUsers(Pageable pageable, Utilisateur demandeur) {
        UtilisateurUtils.isAdmin(demandeur);
        return utilisateurRepository.findAll(pageable).map(utilisateurMapper::toDto);
    }

    /**
     * Créer un nouvel admin après validation des règles métier.
     * Cette démarche n'est possible que pour un superadmin.
     *
     * @param userDto Admin à créer (non encore persisté)
     * @param role    Rôle à affecter au nouvel utilisateur
     * @throws FileNotFoundException si l'adresse associée à l'utilisateur n'est pas trouvée
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     * @throws AccessDeniedException si l'utilisateur connecté n'a pas les droits suffisants
     */
    public void createAdmin(UtilisateurDto userDto, Utilisateur demandeur, RoleUtilisateur role)
            throws FileNotFoundException, BusinessRuleException, TokenExpiredException {

        UtilisateurUtils.isSuperadmin(demandeur);
        Adresse adresse = UtilisateurUtils.findAdresseOrThrow(adresseRepository, userDto.getIdAdresse());
        Utilisateur userToSave = utilisateurMapper.fromDto(userDto, adresse, role);
        utilisateurValidator.validate(userToSave);
        utilisateurRepository.save(userToSave);
    }

    /**
     * Active ou désactive un utilisateur (inversion selon son rôle actuel)
     * Accessible uniquement pour les admins et super admins
     *
     * @param idCible   identifiant de l'utilisateur à activer/désactiver
     * @param demandeur utilisateur à l'origine de la requête
     * @return le message de confirmation du changement de rôle
     * @throws FileNotFoundException si l'utilisateur ciblé n'est pas trouvé
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     * @throws AccessDeniedException si l'utilisateur connecté n'a pas les droits suffisants
     */
    public String toggleActivationUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException {
        return roleManagementService.toggleUserRole(
                demandeur, idCible,
                RoleUtilisateur.INACTIF, RoleUtilisateur.UTILISATEUR,
                "Utilisateur désactivé", "Utilisateur réactivé"
        );
    }

    /**
     * Banni ou débanni un utilisateur (inversion selon son rôle actuel)
     * Accessible uniquement pour les admins et super admins
     *
     * @param idCible   identifiant de l'utilisateur à bannir/débannir
     * @param demandeur utilisateur à l'origine de la requête
     * @return le message de confirmation du changement de rôle
     * @throws FileNotFoundException si l'utilisateur ciblé n'est pas trouvé
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     * @throws AccessDeniedException si l'utilisateur connecté n'a pas les droits suffisants
     */
    public String toggleBanUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException {
        return roleManagementService.toggleUserRole(
                demandeur, idCible,
                RoleUtilisateur.BANNI, RoleUtilisateur.UTILISATEUR,
                "Utilisateur banni", "Utilisateur débanni"
        );
    }
}
