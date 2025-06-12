package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UtilisateurService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    public Utilisateur getUser(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }
}
