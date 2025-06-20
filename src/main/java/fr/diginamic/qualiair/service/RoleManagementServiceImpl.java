package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleManagementServiceImpl implements RoleManagementService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
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
