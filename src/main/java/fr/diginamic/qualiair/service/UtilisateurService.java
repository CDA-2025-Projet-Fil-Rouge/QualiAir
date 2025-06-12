package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import fr.diginamic.qualiair.security.JwtAuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
