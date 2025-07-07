package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.exception.ExternalApiResponseException;

public interface IHttpResponseValidator {
    /**
     * Test de validation, le test en parametre doit etre valide, sinon la methode jette une exception
     *
     * @param condition    test valide à confirmer
     * @param errorMessage message d'erreur le cas échéant
     * @throws ExternalApiResponseException le test a échoué
     */
    default void isTrue(boolean condition, String errorMessage) throws ExternalApiResponseException {
        if (!condition) {
            throw new ExternalApiResponseException(errorMessage);
        }
    }

}
