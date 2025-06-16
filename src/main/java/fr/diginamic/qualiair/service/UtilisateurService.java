package fr.diginamic.qualiair.service;

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
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


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
     * Crée un nouvel admin après validation des règles métier.
     * Cette démarche n'est possible que pour un superadmin.
     *
     * @param userDto Admin à créer (non encore persisté)
     * @param role Rôle à affecter au nouvel utilisateur
     * @throws FileNotFoundException si l'adresse associée à l'utilisateur n'est pas trouvée
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
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
     * Modifie le rôle d'un utilisateur (si actif = désactivé et vice versa)
     * Accessible uniquement pour les admins et super admins
     * @param idCible identifiant de l'utilisateur à activer/désactiver
     * @param demandeur utilisateur à l'origine de la requête
     * @return le message de confirmation du changement de rôle
     * @throws FileNotFoundException si l'utilisateur ciblé n'est pas trouvé
     * @throws BusinessRuleException si les règles métier ne sont pas respectées
     */
    public String toggleRole(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException {
        UtilisateurUtils.isAdmin(demandeur);
        Utilisateur cible = utilisateurRepository.findById(idCible)
                .orElseThrow(() -> new FileNotFoundException("Utilisateur introuvable"));

        if (UtilisateurUtils.isAdmin(cible)) {
            throw new BusinessRuleException("Impossible de désactiver un admin.");
        }
        // Vérifie le rôle de l'utilisateur ciblé afin de procéder au bon changement
        if (cible.getRole() == RoleUtilisateur.INACTIF) {
            cible.setRole(RoleUtilisateur.UTILISATEUR);
            utilisateurRepository.save(cible);
            return "Utilisateur réactivé";
        } else {
            cible.setRole(RoleUtilisateur.INACTIF);
            utilisateurRepository.save(cible);
            return "Utilisateur désactivé";
        }
    }
}
