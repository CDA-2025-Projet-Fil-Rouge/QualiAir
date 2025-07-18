package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UtilisateurService {
    /**
     * Récupérer un utilisateur à partir de son email.
     *
     * @param email Email de l'utilisateur recherché
     * @return l'utilisateur correspondant
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé
     */
    Utilisateur getUser(String email);

    Utilisateur getUserById(Long id) throws DataNotFoundException;

    Utilisateur updateUser(Utilisateur utilisateur) throws BusinessRuleException;

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param idUser identifiant de l'utilisateur connecté accédant à ses informations
     * @return le DTO de l'utilisateur
     * @throws FileNotFoundException si l'utilisateur n'existe pas
     */
    UtilisateurDto getPersonalData(Long idUser) throws FileNotFoundException;

    /**
     * Récupérer la liste paginée de tous les utilisateurs inscrits.
     * Accessible uniquement aux administrateurs et super-administrateurs.
     *
     * @param pageable  objet de pagination contenant le numéro de page, la taille de page, et éventuellement le tri
     * @param demandeur utilisateur authentifié à l'origine de la requête
     * @return une page de UtilisateurDto représentant les utilisateurs
     * @throws AccessDeniedException si l'utilisateur connecté n'a pas les droits suffisants
     */
    Page<UtilisateurDto> getAllUsers(Pageable pageable, Utilisateur demandeur);

    List<UtilisateurDto> getAllUsers(Utilisateur demandeur);

    /**
     * Permet à un utilisateur connecté de modifier ses informations personnelles
     *
     * @param user utilisateur connecté souhaitant modifier ses informations
     * @param dto  dto envoyé dans le corps de la requête
     * @return dto de l'utilisateur modifié
     * @throws BusinessRuleException si une erreur métier est rencontrée
     * @throws FileNotFoundException si la commune indiquée dans l'adresse n'est pas trouvée
     */
    UtilisateurUpdateDto updatePersonalData(Utilisateur user, UtilisateurUpdateDto dto)
            throws BusinessRuleException, FileNotFoundException;

    /**
     * Mise à jour de l'adresse si modifiée lorsque l'utilisateur modifie ses informations personnelles
     *
     * @param user    utilisateur à l'origine de la modification
     * @param userDto instance dto de l'utilisateur, contenant la nouvelle adresse
     * @throws FileNotFoundException si la commune indiquée dans l'adresse n'est pas trouvée
     * @throws BusinessRuleException si une erreur de validation est rencontrée
     */
    void updateAdresse(Utilisateur user, UtilisateurUpdateDto userDto)
            throws FileNotFoundException, BusinessRuleException;

    /**
     * Modifie le rôle d'un utilisateur pour qu'il devienne admin, ou inversement s'il l'est déjà
     * Accessible uniquement pour les admins et super admins
     *
     * @param idCible   identifiant de l'utilisateur à modifier
     * @param demandeur utilisateur à l'origine de la requête
     * @return le message de confirmation de changement de rôle
     * @throws FileNotFoundException si l'utilisateur ciblé n'est pas trouvé
     * @throws BusinessRuleException si l'utilisateur connecté n'a pas les droits suffisants
     */
    String toggleAdminUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException;

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
    String toggleActivationUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException;

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
    String toggleBanUser(Long idCible, Utilisateur demandeur)
            throws FileNotFoundException, BusinessRuleException;

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur} d'une {@link Commune}.
     *
     * @param code le code de la commune
     * @return liste d'e-mails
     * @throws DataNotFoundException aucune donnée récupérée en base
     */
    List<String> getEmailsByCommune(String code) throws DataNotFoundException;

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur} d'un {@link Departement}.
     *
     * @param code le code du departement
     * @return liste d'e-mails
     * @throws DataNotFoundException aucune donnée récupérée en base
     */
    List<String> getEmailsByDepartement(String code) throws DataNotFoundException;

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur} d'une {@link Region}.
     *
     * @param code le code de la commune
     * @return liste d'e-mails
     * @throws ParsedDataException le code reçu n'est pas convertible en int
     */
    List<String> getEmailsByRegion(String code) throws ParsedDataException, DataNotFoundException;

    /**
     * Récupare l'e-mail de tous les {@link Utilisateur}
     *
     * @return liste d'e-mails
     * @throws DataNotFoundException aucune donnée récupérée en base
     */
    List<String> getAllEmails() throws DataNotFoundException;
}
