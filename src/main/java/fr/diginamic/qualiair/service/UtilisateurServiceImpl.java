package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.mapper.AdresseMapper;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import fr.diginamic.qualiair.repository.AdresseRepository;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.AdresseValidator;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static fr.diginamic.qualiair.utils.RegionUtils.toInt;


@Service
public class UtilisateurServiceImpl implements UtilisateurService {

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
    @Autowired
    private AdresseRepository adresseRepository;

    @Override
    public Utilisateur getUser(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    @Override
    public Utilisateur getUserById(Long id) throws DataNotFoundException {
        return utilisateurRepository.findById(id).orElseThrow((() -> new DataNotFoundException("Utilisateur non trouvé pour l'id: " + id)));
    }

    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) throws BusinessRuleException {
        utilisateurValidator.validate(utilisateur);
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public UtilisateurDto getPersonalData(Long idUser) throws FileNotFoundException {
        Utilisateur user = utilisateurRepository.findById(idUser)
                .orElseThrow(() -> new FileNotFoundException("Utilisateur introuvable"));
        return utilisateurMapper.toDto(user);
    }

    @Override
    public Page<UtilisateurDto> getAllUsers(Pageable pageable, Utilisateur demandeur) {
        UtilisateurUtils.isAdmin(demandeur);
        return utilisateurRepository.findAll(pageable).map(utilisateurMapper::toDto);
    }

    @Override
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
     *
     * @param adresse adresse de l'utilisateur à modifier
     * @param dto     instance dto de l'adresse modifiée
     * @return true si l'adresse a été modifiée
     */
    private boolean isAdresseChanged(Adresse adresse, AdresseDto dto) {
        return !Objects.equals(adresse.getNumeroRue(), dto.getNumeroRue()) ||
               !Objects.equals(adresse.getLibelleRue(), dto.getLibelleRue()) ||
               !Objects.equals(adresse.getCommune().getCodePostal(), dto.getCodePostal()) ||
               !Objects.equals(adresse.getCommune().getNomReel(), dto.getNomCommune());
    }

    @Override
    public void updateAdresse(Utilisateur user, UtilisateurUpdateDto userDto)
            throws FileNotFoundException, BusinessRuleException {
        AdresseDto adresseDto = userDto.getAdresseDto();
        Adresse adresse = user.getAdresse();
        if (!isAdresseChanged(adresse, adresseDto)) {
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
     *
     * @param user     utilisateur à l'origine de la modification
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

    @Override
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

    @Override
    public String toggleActivationUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException {
        UtilisateurUtils.isAdmin(demandeur);
        return roleManagementService.toggleUserRole(
                demandeur, idCible,
                RoleUtilisateur.INACTIF, RoleUtilisateur.UTILISATEUR,
                "Utilisateur désactivé", "Utilisateur réactivé"
        );
    }

    @Override
    public String toggleBanUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException {
        UtilisateurUtils.isAdmin(demandeur);
        return roleManagementService.toggleUserRole(
                demandeur, idCible,
                RoleUtilisateur.BANNI, RoleUtilisateur.UTILISATEUR,
                "Utilisateur banni", "Utilisateur débanni"
        );
    }

    @Override
    public List<String> getEmailsByCommune(String code) throws DataNotFoundException {
        List<String> emails = utilisateurRepository.findByAdresse_Commune_CodeInsee(code);
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé dans cette commune: " + code);
        }
        return emails;
    }

    @Override
    public List<String> getEmailsByDepartement(String code) throws DataNotFoundException {
        List<String> emails = utilisateurRepository.findByAdresse_Commune_Departement_Code(code);
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé dans cette commune: " + code);
        }
        return emails;
    }

    @Override
    public List<String> getEmailsByRegion(String code) throws ParsedDataException, DataNotFoundException {
        List<String> emails = utilisateurRepository.findByAdresse_Commune_Departement_Region_Code(toInt(code));
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé dans cette commune: " + code);
        }
        return emails;

    }


    @Override
    public List<String> getAllEmails() throws DataNotFoundException {
        List<String> emails = utilisateurRepository.findAllEmails();
        if (emails.isEmpty()) {
            throw new DataNotFoundException("Aucun utilisateur trouvé");
        }
        return emails;
    }
}
