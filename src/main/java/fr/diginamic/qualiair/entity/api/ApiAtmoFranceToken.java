package fr.diginamic.qualiair.entity.api;

import java.time.LocalDateTime;

public class ApiAtmoFranceToken {

    /**
     * Bearer token required to access the api, valid for 1h
     */
    private String bearerToken;
    /**
     * Time the token was acquired at
     */
    private LocalDateTime localDateTimeTokenObtention;

    public ApiAtmoFranceToken() {
    }

    public ApiAtmoFranceToken(String bearerToken, LocalDateTime localDateTimeTokenObtention) {
        this.bearerToken = bearerToken;
        this.localDateTimeTokenObtention = localDateTimeTokenObtention;
    }

    /**
     * Getter
     *
     * @return bearerToken
     */
    public String getBearerToken() {
        return bearerToken;
    }

    /**
     * Setter
     *
     * @param bearerToken sets value
     */
    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    /**
     * Getter
     *
     * @return localDateTimeTokenObtention
     */
    public LocalDateTime getLocalDateTimeTokenObtention() {
        return localDateTimeTokenObtention;
    }

    /**
     * Setter
     *
     * @param localDateTimeTokenObtention sets value
     */
    public void setLocalDateTimeTokenObtention(LocalDateTime localDateTimeTokenObtention) {
        this.localDateTimeTokenObtention = localDateTimeTokenObtention;
    }
}
