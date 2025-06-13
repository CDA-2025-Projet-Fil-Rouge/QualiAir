package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Commune;
import org.springframework.stereotype.Component;

@Component
public class CommuneValidator implements ICommuneValidator {
    @Override
    public boolean validate(Commune entity) {
        //todo impl
        return true;
    }
}
