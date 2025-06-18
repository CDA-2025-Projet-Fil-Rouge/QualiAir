package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class HistoriqueValidator {
    public void validateParams(NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
    }

    public void validateParams(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
    }

    public void validateParams(String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
    }
}
