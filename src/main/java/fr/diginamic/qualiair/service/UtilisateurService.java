package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.AdresseMapper;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    UtilisateurMapper utilisateurMapper;
    @Autowired
    private UtilisateurValidator utilisateurValidator;
    @Autowired
    private AdresseRepository adresseRepository;
    @Autowired
    RoleManagementService roleManagementService;
    @Autowired
    BCryptPasswordEncoder bcrypt;
    @Autowired
    CommuneRepository communeRepository;
    @Autowired
    private AdresseMapper adresseMapper;


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

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param idUser identifiant de l'utilisateur connecté accédant à ses informations
     * @return le DTO de l'utilisateur
     * @throws FileNotFoundException si l'utilisateur n'existe pas
     */
    public UtilisateurDto getPersonalData(Long idUser) throws FileNotFoundException {
        Utilisateur user = utilisateurRepository.findById(idUser)
                .orElseThrow(() -> new FileNotFoundException("Utilisateur introuvable"));
        return utilisateurMapper.toDto(user);
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
     * Permet à un utilisateur connecté de modifier ses informations personnelles
     *
     * @param user utilisateur connecté souhaitant modifier ses informations
     * @param dto  dto envoyé dans le corps de la requête
     * @return dto de l'utilisateur modifié
     * @throws BusinessRuleException si une erreur métier est rencontrée
     */
    public UtilisateurUpdateDto updatePersonalData(Utilisateur user, UtilisateurUpdateDto dto)
            throws BusinessRuleException {
        CheckUtils.ensureMatchingIds(user.getId(), dto.getId());
        // Si changement d'email, vérifie qu'il n'y a pas de doublons
        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            CheckUtils.ensureUniqueEmail(utilisateurRepository, dto.getEmail());
        }
        utilisateurMapper.updateFromDto(user, dto);
        handlePasswordUpdate(user, dto);
        utilisateurRepository.save(user);
        return utilisateurMapper.toUpdateDto(user);
    }

    /**
     * Gestion de la modification du mot de passe
     *
     * @param user utilisateur souhaitant modifier son mot de passe
     * @param dto  le dto correspondant
     * @throws BusinessRuleException si l'ancien mot de passe du dto ne correspond pas à celui de l'utilisateur
     */
    private void handlePasswordUpdate(Utilisateur user, UtilisateurUpdateDto dto) throws BusinessRuleException {
        if (dto.getNouveauMotDePasse() != null && !dto.getNouveauMotDePasse().isBlank()) {
            if (!bcrypt.matches(dto.getAncienMotDePasse(), user.getMotDePasse())) {
                throw new BusinessRuleException("Ancien mot de passe incorrect.");
            }
            user.setMotDePasse(bcrypt.encode(dto.getNouveauMotDePasse()));
        }
    }

    /**
     * Permet à un utilisateur connecté de modifier son adresse personnelle
     *
     * @param user utilisateur à l'origine de la demande de modification
     * @param dto  instance d'AdresseDto présent dans le corps de la requête
     * @return la nouvelle adresseDto
     * @throws FileNotFoundException si une donnée nécessaire n'est pas trouvée
     */
    @Transactional
    public AdresseDto updateUserAdresse(Utilisateur user, AdresseDto dto)
            throws FileNotFoundException {
        CheckUtils.ensureMatchingIds(user.getAdresse().getId(), dto.getId());

        Adresse adresse = adresseRepository.findById(dto.getId())
                .orElseThrow(() -> new FileNotFoundException("Adresse non trouvée"));

        updateAdresseFromDto(adresse, dto);

        return adresseMapper.toDto(adresse);
    }

    /**
     * Met à jour les champs d'une entité Adresse à partir des données fournies dans un AdresseDto.
     * @param adresse l'entité Adresse à modifier
     * @param dto les nouvelles données de l'adresse transmises par le client
     * @throws FileNotFoundException si la commune spécifiée dans le DTO n'existe pas en base
     */
    private void updateAdresseFromDto(Adresse adresse, AdresseDto dto) throws FileNotFoundException {
        adresse.setNumeroRue(dto.getNumeroRue());
        adresse.setLibelleRue(dto.getLibelleRue());
        Commune commune = communeRepository
                .findByNomPostalAndCodePostal(dto.getNomCommune(), dto.getCodePostal())
                .orElseThrow(() -> new FileNotFoundException("Commune non trouvée"));
        adresse.setCommune(commune);
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

