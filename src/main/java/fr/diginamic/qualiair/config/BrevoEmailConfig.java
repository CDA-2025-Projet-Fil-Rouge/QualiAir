package fr.diginamic.qualiair.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.URI;

@Configuration
@PropertySource("classpath:brevo.properties")
public class BrevoEmailConfig {

    @Value("${brevo.api.key}")
    private String key;
    @Value("${brevo.api.key-attribute}")
    private String keyAttribute;
    @Value("${brevo.api.base-url}")
    private URI baseUrl;
    @Value("${brevo.api.send-email-url}")
    private URI sendEmailUrl;

    /**
     * Getter
     *
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Getter
     *
     * @return keyAttribute
     */
    public String getKeyAttribute() {
        return keyAttribute;
    }

    /**
     * Getter
     *
     * @return baseUrl
     */
    public URI getBaseUrl() {
        return baseUrl;
    }

    /**
     * Getter
     *
     * @return sendEmailUrl
     */
    public URI getSendEmailUrl() {
        return sendEmailUrl;
    }
}
