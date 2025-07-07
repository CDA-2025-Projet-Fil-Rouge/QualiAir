package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

public interface RubriqueService {
    /**
     * Récupère toutes les rubriques existantes en base.
     *
     * @return une liste de RubriqueDto représentant les rubriques disponibles.
     */
    Page<RubriqueDto> getAllRubriques(Pageable pageable);

    /**
     * Crée une nouvelle rubrique, uniquement si l'utilisateur est administrateur.
     *
     * @param dto      les informations de la rubrique à créer.
     * @param createur l'utilisateur connecté, devant avoir le rôle ADMIN.
     * @return la rubrique créée sous forme de DTO.
     * @throws AccessDeniedException si l'utilisateur n'est pas ADMIN.
     * @throws BusinessRuleException si la rubrique ne respecte pas les règles de validation métier.
     */
    RubriqueDto createRubrique(RubriqueDto dto, Utilisateur createur)
            throws BusinessRuleException, TokenExpiredException;

    /**
     * Met à jour une rubrique existante si l'utilisateur est un administrateur.
     *
     * @param idRubrique   identifiant de la rubrique à modifier.
     * @param dto          les nouvelles données de la rubrique.
     * @param modificateur l'utilisateur connecté tentant la modification.
     * @return la rubrique modifiée sous forme de DTO.
     * @throws AccessDeniedException    si l'utilisateur n'est pas admin.
     * @throws IllegalArgumentException si la rubrique est introuvable ou invalide.
     * @throws BusinessRuleException    si la rubrique modifiée ne respecte pas les règles métier.
     */
    RubriqueDto updateRubrique(Long idRubrique, RubriqueDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException;

    /**
     * Supprime une rubrique existante, à la condition que l'utilisateur soit admin et que la rubrique ne contienne pas de topic.
     *
     * @param idRubrique identifiant de la rubrique à supprimer.
     * @param user       l'utilisateur connecté tentant la suppression.
     * @throws AccessDeniedException si l'utilisateur n'est pas admin.
     * @throws FileNotFoundException si la rubrique est introuvable ou invalide.
     * @throws BusinessRuleException si la tentative de suppression ne respecte pas les règles métier.
     */
    void deleteRubrique(Long idRubrique, Utilisateur user)
            throws FileNotFoundException, BusinessRuleException;
}
