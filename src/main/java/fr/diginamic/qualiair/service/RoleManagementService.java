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

@Service
public class RoleManagementService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Gère le changement de rôle générique
     * @param demandeur utilisateur à l'origine de la modification
     * @param idCible identifiant de l'utilisateur concerné par la modification
     * @param cibleRole rôle ciblé lors du premier changement (incatif/banni)
     * @param roleAlternatif rôle à rétablir en cas de second changement (actif/utilisateur)
     * @param messageToSet message correspondant au changement vers le rôle ciblé
     * @param messageToRevert message correspondant au changement vers le rôleAlternatif
     * @return message si succès
     * @throws FileNotFoundException si l'utilisateur cible n'est pas trouvé
     * @throws AccessDeniedException si le demandeur ne possède pas les droits suffisants
     */
    public String toggleUserRole(Utilisateur demandeur, Long idCible,
                                 RoleUtilisateur cibleRole, RoleUtilisateur roleAlternatif,
                                 String messageToSet, String messageToRevert)
            throws FileNotFoundException, BusinessRuleException {

        UtilisateurUtils.isAdmin(demandeur);
        Utilisateur cible = UtilisateurUtils.findUserOrThrow(utilisateurRepository, idCible);

        if (UtilisateurUtils.isAdmin(cible)) {
            throw new BusinessRuleException("Impossible de bannir un administrateur.");
        }

        RoleUtilisateur nouveauRole = UtilisateurUtils.computeNextRole(
                cible.getRole(), cibleRole, roleAlternatif);

        cible.setRole(nouveauRole);
        utilisateurRepository.save(cible);

        return UtilisateurUtils.messageForRoleChange(
                nouveauRole, cibleRole, messageToSet, messageToRevert);
    }
}