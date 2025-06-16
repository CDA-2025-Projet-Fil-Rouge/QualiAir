package fr.diginamic.qualiair.entity.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiAtmoFranceTest {
    @Autowired
    private ApiAtmoFrance api;

    @Test
    void printStuff() {
        StringBuilder sb = new StringBuilder();

        sb.append(api.getUriAirQuality())
                .append("\n")
                .append(api.getUriLogin())
                .append("\n")
                .append(api.getUtilisateur()
                        .getUsername())
                .append("\n")
                .append(api.getUriAirQuality());

        System.out.println(sb);
    }
}
