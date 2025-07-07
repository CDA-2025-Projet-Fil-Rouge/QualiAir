package fr.diginamic.qualiair.entity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Classe rassemblant les données utiles à l'utilsation de l'api Atmo-France
 */
@Component
public class ApiAtmoFrance {

    /**
     * URI de login
     */
    @Value("${atmo.uri.login}")
    private URI uriLogin;
    /**
     * URI pour les requetes AirQualité, plus d'information dans la documentation officielles : "//todo"
     */
    @Value("${atmo.uri.air-quality}")
    private URI uriAirQuality;

    @Autowired
    private UtilisateurAtmoFrance utilisateur;

    private AtmoFranceToken token;

    public ApiAtmoFrance() {
    }

    /**
     * Getter
     *
     * @return uriLogin
     */
    public URI getUriLogin() {
        return uriLogin;
    }

    /**
     * Getter
     *
     * @return uriAirQuality
     */
    public URI getUriAirQuality() {
        return uriAirQuality;
    }

    /**
     * Getter
     *
     * @return token
     */
    public AtmoFranceToken getToken() {
        return token;
    }

    /**
     * Setter
     *
     * @param token sets value
     */
    public void setToken(AtmoFranceToken token) {
        this.token = token;
    }

    /**
     * Getter
     *
     * @return utilisateur
     */
    public UtilisateurAtmoFrance getUtilisateur() {
        return utilisateur;
    }
}
