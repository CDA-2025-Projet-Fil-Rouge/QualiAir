package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.security.JwtAuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    BCryptPasswordEncoder bcrypt;
    @Autowired
    JwtAuthentificationService jwtAuthentificationService;

    public Utilisateur getUser(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }

    public ResponseCookie logUser(Utilisateur user) throws Exception {
        Utilisateur utilisateur = getUser(user.getEmail());
        if (bcrypt.matches(user.getMotDePasse(), utilisateur.getMotDePasse())) {
            return jwtAuthentificationService.generateToken(utilisateur);
        }
        throw new BadCredentialsException("Email ou mot de passe incorrect");
    }

    public void createUser(Utilisateur user, RoleUtilisateur role) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("L'email est obligatoire.");
        }
        if (user.getMotDePasse() == null || user.getMotDePasse().isBlank()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire.");
        }
        if (user.getPrenom() == null || user.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom est obligatoire.");
        }
        if (user.getNom() == null || user.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (user.getAdresse() == null) {
            throw new IllegalArgumentException("L'adresse est obligatoire.");
        }
        if (utilisateurRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
        }

        Utilisateur userToSave = new Utilisateur();
        userToSave.setEmail(user.getEmail());
        userToSave.setMotDePasse(bcrypt.encode(user.getMotDePasse()));
        userToSave.setPrenom(user.getPrenom());
        userToSave.setNom(user.getNom());
        Optional.ofNullable(user.getDateNaissance()).ifPresent(userToSave::setDateNaissance);
        userToSave.setAdresse(user.getAdresse());
        userToSave.setDateInscription(LocalDateTime.now());
        userToSave.setRole(role);

        utilisateurRepository.save(userToSave);
    }
}
