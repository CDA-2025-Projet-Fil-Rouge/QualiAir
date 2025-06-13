package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;

public interface IValidator<T> {
    /**
     * Test de validation, le test en parametre doit etre valide, sinon la methode jette une exception
     *
     * @param condition    test valide à confirmer
     * @param errorMessage message d'erreur le cas échéant
     * @throws fr.diginamic.qualiair.exception.BusinessRuleException le test a échoué
     */
    default void isTrue(boolean condition, String errorMessage) throws BusinessRuleException {
        if (!condition) {
            throw new BusinessRuleException(errorMessage);
        }
    }

    boolean validate(T entity) throws TokenExpiredException, BusinessRuleException;
}
