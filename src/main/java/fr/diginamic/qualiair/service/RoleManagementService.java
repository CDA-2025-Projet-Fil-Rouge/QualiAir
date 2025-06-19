package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import org.springframework.security.access.AccessDeniedException;

public interface RoleManagementService {
    /**
     * Gère le changement de rôle générique
     *
     * @param demandeur       utilisateur à l'origine de la modification
     * @param idCible         identifiant de l'utilisateur concerné par la modification
     * @param cibleRole       rôle ciblé lors du premier changement (incatif/banni)
     * @param roleAlternatif  rôle à rétablir en cas de second changement (actif/utilisateur)
     * @param messageToSet    message correspondant au changement vers le rôle ciblé
     * @param messageToRevert message correspondant au changement vers le rôleAlternatif
     * @return message si succès
     * @throws FileNotFoundException si l'utilisateur cible n'est pas trouvé
     * @throws AccessDeniedException si le demandeur ne possède pas les droits suffisants
     */
    String toggleUserRole(Utilisateur demandeur, Long idCible,
                          RoleUtilisateur cibleRole, RoleUtilisateur roleAlternatif,
                          String messageToSet, String messageToRevert)
            throws FileNotFoundException, BusinessRuleException;
}
