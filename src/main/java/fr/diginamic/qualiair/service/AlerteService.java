package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.notification.DemandeNotification;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.TypeAlerte;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.RouteParamException;
import org.springframework.data.domain.Page;

public interface AlerteService {
    Page<AlerteInfo> getAlertesByPolluant(AirPolluant polluant, int page, int size, int maxIndice);

    void sendAlert(DemandeNotification notification) throws RouteParamException, ExternalApiResponseException, ParsedDataException, DataNotFoundException;

    String buildMessage(TypeAlerte alerte, String code, String message);
}
