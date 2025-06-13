package fr.diginamic.qualiair.entity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ApiAtmoFrance {

    @Value("${external.api.atmo.uri.login}")
    private URI uriLogin;
    @Value("${external.api.atmo.uri.air-quality}")
    private URI uriAirQuality;

    @Autowired
    private UtilisateurAtmoFrance utilisateur;

    private ApiAtmoFranceToken token;

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
    public ApiAtmoFranceToken getToken() {
        return token;
    }

    /**
     * Setter
     *
     * @param token sets value
     */
    public void setToken(ApiAtmoFranceToken token) {
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
