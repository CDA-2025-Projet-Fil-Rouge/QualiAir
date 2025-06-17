package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

@Component
public class MesureValidator implements IMesureValidator {

    @Override
    public boolean validate(MesurePrevision entity) throws BusinessRuleException {
        return true;
    }
}
