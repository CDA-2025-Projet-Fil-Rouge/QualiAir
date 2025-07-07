package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Departement;

public interface IDepartementValidator extends IValidator<Departement> {
    @Override
    boolean validate(Departement entity);
}
