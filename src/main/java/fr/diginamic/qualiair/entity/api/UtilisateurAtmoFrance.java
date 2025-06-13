package fr.diginamic.qualiair.entity.api;

public class UtilisateurAtmoFrance {

    private final String username;
    private final String password;


    public UtilisateurAtmoFrance(String username, String password) {
        this.username = username;
        this.password = password;
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
