package fr.diginamic.qualiair.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Configuration
public class HttpConfig {

    /**
     * RestTemplate instanciateur
     *
     * @param builder builder
     * @return resttemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.connectTimeout(Duration.of(5, ChronoUnit.SECONDS)).readTimeout(Duration.of(20, ChronoUnit.SECONDS)).build();
    }
}
