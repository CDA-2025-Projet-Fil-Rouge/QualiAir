package fr.diginamic.qualiair.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Classe de configuration dédiée à l'api OpenWeather. Les secrets sont contenus dans un fichier openweather.properties
 */
@Configuration
@PropertySource("classpath:openweather.properties")
public class OpenWeatherConfig {

}
