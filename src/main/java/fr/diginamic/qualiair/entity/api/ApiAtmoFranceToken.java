package fr.diginamic.qualiair.entity.api;

import java.time.LocalDateTime;

public class ApiAtmoFranceToken extends ApiToken {


    /**
     * Time the token was acquired at
     */
    private final LocalDateTime localDateTimeTokenObtention;

    public ApiAtmoFranceToken() {
        this.localDateTimeTokenObtention = LocalDateTime.now();
    }

    public ApiAtmoFranceToken(ApiToken bearerToken, LocalDateTime localDateTimeTokenObtention) {
        this.localDateTimeTokenObtention = LocalDateTime.now();
    }

    /**
     * Getter
     *
     * @return localDateTimeTokenObtention
     */
    public LocalDateTime getLocalDateTimeTokenObtention() {
        return localDateTimeTokenObtention;
    }

}
