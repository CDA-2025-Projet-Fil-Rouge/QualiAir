package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;

public interface UtilisateurMapper {
    /**
     * Convertit une entité Utilisateur en UtilisateurDto
     *
     * @param user l'entité à convertir
     * @return le DTO correspondant
     */
    UtilisateurDto toDto(Utilisateur user);

    /**
     * Convertit un UtilisateurDto en entité Utilisateur.
     * Ne renseigne que les champs transmis depuis le client.
     *
     * @param dto     le DTO source
     * @param adresse l'adresse associée à l'utilisateur
     * @param role    désigne le rôle de l'utilisateur
     * @return l'entité partiellement construite
     */
    Utilisateur fromDto(UtilisateurDto dto, Adresse adresse, RoleUtilisateur role);

    /**
     * Met à jour les champs personnels d’un utilisateur existant à partir d’un UtilisateurUpdateDto.
     *
     * @param utilisateur l'entité à mettre à jour
     * @param dto         les nouvelles données utilisateur
     */
    void updateFromDto(Utilisateur utilisateur, UtilisateurUpdateDto dto);

    /**
     * Convertit un utilisateur en UtilisateurUpdateDto après modification de ses informations
     *
     * @param user utilisateur modifié à convertir
     * @return le dto correspondant
     */
    UtilisateurUpdateDto toUpdateDto(Utilisateur user);
}
