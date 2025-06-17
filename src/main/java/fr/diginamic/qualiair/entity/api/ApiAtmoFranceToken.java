package fr.diginamic.qualiair.entity.api;

import java.time.LocalDateTime;

/**
 * Token de l'api Atmo France, étant valide 1h ce token doit toujours être confirmé avant utilisation
 */
public class ApiAtmoFranceToken extends ApiToken {


    /**
     * Time the token was acquired at
     */
    private final LocalDateTime obtentionDate;

    public ApiAtmoFranceToken() {
        this.obtentionDate = LocalDateTime.now();
    }

    public ApiAtmoFranceToken(ApiToken bearerToken, LocalDateTime obtentionDate) {
        this.obtentionDate = LocalDateTime.now();
    }

    /**
     * Getter
     *
     * @return localDateTimeTokenObtention
     */
    public LocalDateTime getObtentionDate() {
        return obtentionDate;
    }

}
