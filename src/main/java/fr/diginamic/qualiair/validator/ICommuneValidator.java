package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Commune;

public interface ICommuneValidator extends IValidator<Commune> {
    @Override
    void validate(Commune entity);
}
