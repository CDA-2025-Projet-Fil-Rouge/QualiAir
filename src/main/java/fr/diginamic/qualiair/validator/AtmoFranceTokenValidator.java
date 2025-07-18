package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.api.AtmoFranceToken;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AtmoFranceTokenValidator implements IApiAtmoFranceTokenValidator {

    @Override
    public boolean validate(AtmoFranceToken entity) throws TokenExpiredException, BusinessRuleException {
        LocalDateTime timestamp = entity.getObtentionDate();

        Duration duration = Duration.between(timestamp, LocalDateTime.now());

        isTrue((duration.toHours() <= 1), "Token expiré");
        return true;
    }
}
