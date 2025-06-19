package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.entitesDto.UtilisateurDto;
import fr.diginamic.qualiair.dto.entitesDto.UtilisateurUpdateDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UtilisateurMapper {

    @Autowired
    BCryptPasswordEncoder bcrypt;

    /**
     * Convertit une entité Utilisateur en UtilisateurDto
     *
     * @param user l'entité à convertir
     * @return le DTO correspondant
     */
    public UtilisateurDto toDto(Utilisateur user) {
        UtilisateurDto userDto = new UtilisateurDto();
        userDto.setId(user.getId());
        userDto.setPrenom(user.getPrenom());
        userDto.setNom(user.getNom());
        if(user.getDateNaissance() != null) {
            userDto.setDateNaissance(user.getDateNaissance());
        }
        userDto.setEmail(user.getEmail());
        userDto.setDateInscription(user.getDateInscription());
        userDto.setRole(user.getRole());

        return userDto;
    }


    /**
     * Convertit un UtilisateurDto en entité Utilisateur.
     * Ne renseigne que les champs transmis depuis le client.
     *
     * @param dto le DTO source
     * @param adresse l'adresse associée à l'utilisateur
     * @param role désigne le rôle de l'utilisateur
     * @return l'entité partiellement construite
     */
    public Utilisateur fromDto(UtilisateurDto dto, Adresse adresse, RoleUtilisateur role) {
        Utilisateur user = new Utilisateur();
        user.setEmail(dto.getEmail());
        user.setMotDePasse(bcrypt.encode(dto.getMotDePasse()));
        user.setPrenom(dto.getPrenom());
        user.setNom(dto.getNom());
        Optional.ofNullable(dto.getDateNaissance()).ifPresent(user::setDateNaissance);
        user.setDateInscription(LocalDateTime.now());
        user.setRole(role);
        user.setAdresse(adresse);
        return user;
    }

    /**
     * Met à jour les champs personnels d’un utilisateur existant à partir d’un UtilisateurUpdateDto.
     *
     * @param utilisateur l'entité à mettre à jour
     * @param dto les nouvelles données utilisateur
     */
    public void updateFromDto(Utilisateur utilisateur, UtilisateurUpdateDto dto) {
        if (dto.getPrenom() != null) {
            utilisateur.setPrenom(dto.getPrenom());
        }
        if (dto.getNom() != null) {
            utilisateur.setNom(dto.getNom());
        }
        if (dto.getEmail() != null) {
            utilisateur.setEmail(dto.getEmail());
        }
    }

    /**
     * Convertit un utilisateur en UtilisateurUpdateDto après modification de ses informations
     * @param user utilisateur modifié à convertir
     * @return le dto correspondant
     */
    public UtilisateurUpdateDto toUpdateDto(Utilisateur user) {
        UtilisateurUpdateDto dto = new UtilisateurUpdateDto();
        dto.setId(user.getId());
        dto.setPrenom(user.getPrenom());
        dto.setNom(user.getNom());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
