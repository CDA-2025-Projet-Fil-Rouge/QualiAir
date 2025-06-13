package fr.diginamic.qualiair.entity.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurAtmoFrance {

    @Value("${external.api.atmo.identifiant}")
    private String username;
    @Value("${external.api.atmo.mot-de-passe}")
    private String password;


    public UtilisateurAtmoFrance() {
    }

    /**
     * Getter
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }
}
