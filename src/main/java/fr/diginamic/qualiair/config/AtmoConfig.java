package fr.diginamic.qualiair.config;

import fr.diginamic.qualiair.entity.api.AtmoFranceToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;

/**
 * Classe de configuration dédiée à l'API Atmo-France. Les secrets sont contenus dans le fichier atmo.properties
 */
@Configuration
@PropertySource("classpath:atmo.properties")
public class AtmoConfig {


    @Value("${atmo.identifiant}")
    private String username;
    @Value("${atmo.mot-de-passe}")
    private String password;
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

    private AtmoFranceToken token;

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
}
