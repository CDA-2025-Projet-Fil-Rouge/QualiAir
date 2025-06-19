package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.mapper.AdresseMapper;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.AdresseValidator;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static fr.diginamic.qualiair.utils.RegionUtils.toInt;

import java.util.Objects;


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
    private BCryptPasswordEncoder bcrypt;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private AdresseValidator adresseValidator;
    @Autowired
    private AdresseMapper adresseMapper;

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
     * Permet à un utilisateur connecté de modifier ses informations personnelles
     *
     * @param user utilisateur connecté souhaitant modifier ses informations
     * @param dto  dto envoyé dans le corps de la requête
     * @return dto de l'utilisateur modifié
     * @throws BusinessRuleException si une erreur métier est rencontrée
     * @throws FileNotFoundException si la commune indiquée dans l'adresse n'est pas trouvée
     */
    public UtilisateurUpdateDto updatePersonalData(Utilisateur user, UtilisateurUpdateDto dto)
            throws BusinessRuleException, FileNotFoundException {
        CheckUtils.ensureMatchingIds(user.getId(), dto.getId());
        checkEmailIfChanged(user, dto.getEmail());

        updateAdresse(user, dto);
        utilisateurMapper.updateFromDto(user, dto);
        handlePasswordUpdate(user, dto);

        utilisateurValidator.validate(user);
        utilisateurRepository.save(user);
        return utilisateurMapper.toUpdateDto(user);
    }

    /**
     * Vérifie si l'adresse est concernée par la modification de l'utilisateur
     * @param adresse adresse de l'utilisateur à modifier
     * @param dto instance dto de l'adresse modifiée
     * @return true si l'adresse a été modifiée
     */
    private boolean isAdresseChanged(Adresse adresse, AdresseDto dto) {
        return !Objects.equals(adresse.getNumeroRue(), dto.getNumeroRue()) ||
                !Objects.equals(adresse.getLibelleRue(), dto.getLibelleRue()) ||
                !Objects.equals(adresse.getCommune().getCodePostal(), dto.getCodePostal()) ||
                !Objects.equals(adresse.getCommune().getNomReel(), dto.getNomCommune());
    }

    /**
     * Mise à jour de l'adresse si modifiée lorsque l'utilisateur modifie ses informations personnelles
     * @param user utilisateur à l'origine de la modification
     * @param userDto instance dto de l'utilisateur, contenant la nouvelle adresse
     * @throws FileNotFoundException si la commune indiquée dans l'adresse n'est pas trouvée
     * @throws BusinessRuleException si une erreur de validation est rencontrée
     */
    public void updateAdresse(Utilisateur user, UtilisateurUpdateDto userDto)
    throws FileNotFoundException, BusinessRuleException {
        AdresseDto adresseDto = userDto.getAdresseDto();
        Adresse adresse = user.getAdresse();
        if(!isAdresseChanged(adresse, adresseDto)) {
            return;
        }
        Commune commune = UtilisateurUtils.findCommuneOrThrow(
                communeRepository, adresseDto.getNomCommune(), adresseDto.getCodePostal());

        Adresse adresseToValidate = adresseMapper.fromDto(adresseDto, commune);
        adresseValidator.validate(adresseToValidate);

        adresse.setNumeroRue(adresseToValidate.getNumeroRue());
        adresse.setLibelleRue(adresseToValidate.getLibelleRue());
        adresse.setCommune(adresseToValidate.getCommune());
    }

    /**
     * Vérifie l'adresse email si elle est changée lors de la demande de modification des données personnelles,
     * afin de s'assurer qu'elle est unique
     * @param user utilisateur à l'origine de la modification
     * @param newEmail email éventuellement modifié
     * @throws BusinessRuleException si l'email existe déjà en base
     */
    private void checkEmailIfChanged(Utilisateur user, String newEmail) throws BusinessRuleException {
        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            CheckUtils.ensureUniqueEmail(utilisateurRepository, newEmail);
        }
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
     * Modifie le rôle d'un utilisateur pour qu'il devienne admin, ou inversement s'il l'est déjà
     * Accessible uniquement pour les admins et super admins
     * @param idCible identifiant de l'utilisateur à modifier
     * @param demandeur utilisateur à l'origine de la requête
     * @return le message de confirmation de changement de rôle
     * @throws FileNotFoundException si l'utilisateur ciblé n'est pas trouvé
     * @throws BusinessRuleException si l'utilisateur connecté n'a pas les droits suffisants
     */
    public String toggleAdminUser(Long idCible, Utilisateur demandeur)
        throws FileNotFoundException, BusinessRuleException {
        UtilisateurUtils.isAdmin(demandeur);
        return roleManagementService.toggleUserRole(
                demandeur, idCible,
                RoleUtilisateur.ADMIN, RoleUtilisateur.UTILISATEUR,
                "Rôle mis à jour : utilisateur devenu admin",
                "Rôle mis à jour: admin devenu utilisateur"
        );
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
        UtilisateurUtils.isAdmin(demandeur);
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
        UtilisateurUtils.isAdmin(demandeur);
        return roleManagementService.toggleUserRole(
                demandeur, idCible,
                RoleUtilisateur.BANNI, RoleUtilisateur.UTILISATEUR,
                "Utilisateur banni", "Utilisateur débanni"
        );
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
            throws FileNotFoundException, BusinessRuleException {

        UtilisateurUtils.isSuperadmin(demandeur);
        Adresse adresse = UtilisateurUtils.findAdresseOrThrow(adresseRepository, userDto.getIdAdresse());
        Utilisateur userToSave = utilisateurMapper.fromDto(userDto, adresse, role);
        utilisateurValidator.validate(userToSave);
        utilisateurRepository.save(userToSave);
    }

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur} d'une {@link Commune}.
     *
     * @param code le code de la commune
     * @return liste d'e-mails
     * @throws DataNotFoundException aucune donnée récupérée en base
     */
    public List<String> getEmailsByCommune(String code) throws DataNotFoundException {
        List<String> emails = utilisateurRepository.findByAdresse_Commune_CodeInsee(code);
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé dans cette commune: " + code);
        }
        return emails;
    }

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur} d'un {@link Departement}.
     *
     * @param code le code du departement
     * @return liste d'e-mails
     * @throws DataNotFoundException aucune donnée récupérée en base
     */
    public List<String> getEmailsByDepartement(String code) throws DataNotFoundException {
        List<String> emails = utilisateurRepository.findByAdresse_Commune_Departement_Code(code);
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé dans cette commune: " + code);
        }
        return emails;
    }

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur} d'une {@link Region}.
     *
     * @param code le code de la commune
     * @return liste d'e-mails
     * @throws ParsedDataException le code reçu n'est pas convertible en int
     */
    public List<String> getEmailsByRegion(String code) throws ParsedDataException, DataNotFoundException {
        List<String> emails = utilisateurRepository.findByAdresse_Commune_Departement_Region_Code(toInt(code));
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé dans cette commune: " + code);
        }
        return emails;

    }


    /**
     * Récupare l'e-mail de tous les {@link Utilisateur}
     *
     * @return liste d'e-mails
     * @throws DataNotFoundException aucune donnée récupérée en base
     */
    public List<String> getAllEmails() throws DataNotFoundException {
        List<String> emails = utilisateurRepository.findAllEmails();
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé");
        }
        return emails;
    }
}
