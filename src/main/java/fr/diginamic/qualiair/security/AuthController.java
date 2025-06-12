package fr.diginamic.qualiair.security;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utilisateur user) throws Exception
    {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, utilisateurService.logUser(user).toString())
                .body("vous êtes connecté");
    }
}
