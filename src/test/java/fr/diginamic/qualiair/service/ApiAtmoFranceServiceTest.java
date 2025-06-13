package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.api.ApiAtmoFranceToken;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ApiAtmoFranceServiceTest {

    @Autowired
    private ApiAtmoFranceService apiAtmoFranceService;

    @Test
    void requestToken() throws ExternalApiResponseException {
        ApiAtmoFranceToken token = apiAtmoFranceService.requestToken();
        System.out.println(token.getToken());
        assertTrue(token.getToken().length() >= 15);
    }
}
