package fr.diginamic.qualiair.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@OpenAPIDefinition(servers = @Server(url = "https://api.example.com"))
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API QualiAir")
                        .version("1.0")
                        .description("API mettant à disposition des données lièes à la qualité de l'air et la meteo des villes françaises.")
                        .termsOfService("OPEN DATA")
                        .contact(
                                new Contact().name("Nom du contact").email("email@exemple.com").url("URL du contact"))
                        .license(new License().name("rêve air bert").url("dot")));
    }
}
