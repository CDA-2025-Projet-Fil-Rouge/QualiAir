package fr.diginamic.qualiair.entity.api;

public class ApiToken {

    private String token;

    public ApiToken() {
    }

    public ApiToken(String token) {
        this.token = token;
    }

    /**
     * Getter
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter
     *
     * @param token sets value
     */
    public void setToken(String token) {
        this.token = token;
    }
}
