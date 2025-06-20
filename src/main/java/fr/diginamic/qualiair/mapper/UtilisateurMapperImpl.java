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
public class UtilisateurMapperImpl implements UtilisateurMapper {

    @Autowired
    BCryptPasswordEncoder bcrypt;
    @Autowired
    private AdresseMapper adresseMapper;

    @Override
    public UtilisateurDto toDto(Utilisateur user) {
        UtilisateurDto userDto = new UtilisateurDto();
        userDto.setId(user.getId());
        userDto.setPrenom(user.getPrenom());
        userDto.setNom(user.getNom());
        if (user.getDateNaissance() != null) {
            userDto.setDateNaissance(user.getDateNaissance());
        }
        userDto.setEmail(user.getEmail());
        userDto.setDateInscription(user.getDateInscription());
        userDto.setRole(user.getRole());
        userDto.setAdresseDto(adresseMapper.toDto(user.getAdresse()));

        return userDto;
    }


    @Override
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

    @Override
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

    @Override
    public UtilisateurUpdateDto toUpdateDto(Utilisateur user) {
        UtilisateurUpdateDto dto = new UtilisateurUpdateDto();
        dto.setId(user.getId());
        dto.setPrenom(user.getPrenom());
        dto.setNom(user.getNom());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
