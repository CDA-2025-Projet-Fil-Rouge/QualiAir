package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.stereotype.Component;

@Component
public class MesurePrevisionValidator implements IMesurePrevisionValidator {

    @Override
    public boolean validate(MesurePrevision entity) throws TokenExpiredException, BusinessRuleException {
        return false;
    }
}
