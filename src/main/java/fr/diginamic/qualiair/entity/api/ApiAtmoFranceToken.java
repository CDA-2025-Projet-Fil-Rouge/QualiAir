package fr.diginamic.qualiair.entity.api;

import java.time.LocalDateTime;

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
