package fr.diginamic.qualiair.dto.email;

public class Sender {
    private String name;
    private String email;

    Sender() {
    }

    Sender(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Getter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter
     *
     * @param name sets value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter
     *
     * @param email sets value
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
