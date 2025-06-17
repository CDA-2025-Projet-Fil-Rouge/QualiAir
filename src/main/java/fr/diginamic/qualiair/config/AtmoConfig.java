package fr.diginamic.qualiair.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Classe de configuration dédiée à l'API Atmo-France. Les secrets sont contenus dans le fichier atmo.properties
 */
@Configuration
@PropertySource("classpath:atmo.properties")
public class AtmoConfig {
}
