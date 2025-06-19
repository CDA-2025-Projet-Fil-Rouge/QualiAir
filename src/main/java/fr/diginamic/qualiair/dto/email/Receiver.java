package fr.diginamic.qualiair.dto.email;

import fr.diginamic.qualiair.annotation.DoNotUseDirectly;

@DoNotUseDirectly(useInstead = EmailBuilder.class)
public class Receiver {
    private String email;

    Receiver() {
    }

    Receiver(String email) {
        this.email = email;
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
