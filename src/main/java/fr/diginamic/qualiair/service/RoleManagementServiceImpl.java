package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import static fr.diginamic.qualiair.utils.UtilisateurUtils.isAdmin;

/**
 * Service permettant de modifier le rôle d'un utilisateur, d'un rôle cible vers un rôle alternatif,
 * permettant l'inversion du changement effectué quels que soient ces rôles.
 * Uniquement disponible pour les administrateurs.
 * Si l'utilisateur cible est lui-même un administrateur, il ne peut pas être inactivé ou banni.
 */
@Service
public class RoleManagementServiceImpl implements RoleManagementService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public String toggleUserRole(Utilisateur demandeur, Long idCible,
                                 RoleUtilisateur cibleRole, RoleUtilisateur roleAlternatif,
                                 String messageToSet, String messageToRevert)
            throws FileNotFoundException, BusinessRuleException {

        if(!isAdmin(demandeur)) {
            throw new AccessDeniedException("Fonction réservée aux administrateurs.");
        }

        Utilisateur cible = UtilisateurUtils.findUserOrThrow(utilisateurRepository, idCible);
        if (UtilisateurUtils.isAdmin(cible) &&
                (cibleRole == RoleUtilisateur.BANNI || cibleRole == RoleUtilisateur.INACTIF)) {
            throw new BusinessRuleException("Impossible de bannir ou désactiver un administrateur.");
        }

        RoleUtilisateur nouveauRole = UtilisateurUtils.computeNextRole(
                cible.getRole(), cibleRole, roleAlternatif);

        cible.setRole(nouveauRole);
        utilisateurRepository.save(cible);

        return UtilisateurUtils.messageForRoleChange(
                nouveauRole, cibleRole, messageToSet, messageToRevert);
    }
}
