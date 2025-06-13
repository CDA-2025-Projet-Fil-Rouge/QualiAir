package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Region;

public interface IRegionValidator extends IValidator<Region> {
    @Override
    boolean validate(Region entity);
}
