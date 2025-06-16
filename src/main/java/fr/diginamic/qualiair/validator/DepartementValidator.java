package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementValidator implements IDepartementValidator {
    @Override
    public boolean validate(Departement entity) {
        //todo impl
        return true;
    }
}
