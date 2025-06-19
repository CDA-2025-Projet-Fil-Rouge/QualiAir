package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

@Component
public class AdresseValidator implements IAdresseValidator {

    /**
     * Valide les règles métier liées à une adresse.
     *
     * @param adresse l'adresse à valider
     * @return true si tout est valide
     * @throws BusinessRuleException si une règle est violée
     */
    @Override
    public boolean validate(Adresse adresse) throws BusinessRuleException {
        isTrue(adresse != null, "Les données d'adresse sont manquantes.");

        String numeroRue = adresse.getNumeroRue();
        isTrue(numeroRue != null, "Le numéro de rue est obligatoire.");
        isTrue(!numeroRue.trim().isEmpty(), "Le numéro de rue ne peut pas être vide.");
        isTrue(numeroRue.matches("\\d+"), "Le numéro de rue doit être un nombre positif.");

        String libelleRue = adresse.getLibelleRue();
        isTrue(libelleRue != null, "Le libellé de rue est obligatoire.");
        isTrue(!libelleRue.trim().isEmpty(), "Le libellé de rue ne peut pas être vide.");

        return true;
    }
}
