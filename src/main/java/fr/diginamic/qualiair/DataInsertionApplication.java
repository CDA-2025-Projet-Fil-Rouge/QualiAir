package fr.diginamic.qualiair;

import fr.diginamic.qualiair.service.RecensementParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@Profile("commandLineApp")
public class DataInsertionApplication implements CommandLineRunner {
    @Autowired
    private RecensementParserService recensementParserService;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "commandLineApp");
        SpringApplication app = new SpringApplication(DataInsertionApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        recensementParserService.saveCommunesFromFichier();
    }
}
