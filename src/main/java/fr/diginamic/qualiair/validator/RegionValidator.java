package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Region;
import org.springframework.stereotype.Component;

@Component
public class RegionValidator implements IRegionValidator {
    @Override
    public boolean validate(Region entity) {
        //todo impl
        return true;
    }
}
