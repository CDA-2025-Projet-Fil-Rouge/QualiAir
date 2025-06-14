package fr.diginamic.qualiair.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:openweather.properties")
public class OpenWeatherConfig {

}
