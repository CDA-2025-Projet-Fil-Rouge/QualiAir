package fr.diginamic.qualiair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("api")
public class QualiAirApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(QualiAirApplication.class, args);
    }
}
