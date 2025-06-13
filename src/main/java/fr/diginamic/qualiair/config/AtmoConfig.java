package fr.diginamic.qualiair.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:atmo.properties")
public class AtmoConfig {
}
