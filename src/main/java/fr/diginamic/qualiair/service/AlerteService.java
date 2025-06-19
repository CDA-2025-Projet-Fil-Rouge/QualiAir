package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.AlerteInfo;
import fr.diginamic.qualiair.dto.DemandeNotification;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.TypeAlerte;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.RouteParamException;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AlerteService {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private CommuneService communeService;
    @Autowired
    private MesureAirService mesureAirService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MesureAirMapper mapper;

    private static void validateParams(TypeAlerte type, String code, String message) throws RouteParamException {
        if (type != TypeAlerte.NATIONAL && code == null || code.trim().isEmpty()) {
            throw new RouteParamException("Un code est requis pour le type d'alerte" + type);
        }
        if (message == null || message.trim().isEmpty()) {
            throw new RouteParamException("Le message ne doit pas être vide");
        }
    }

    public Page<AlerteInfo> getAlertesByPolluant(AirPolluant polluant, int page, int size, int maxIndice) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateReleve").descending());
        Page<MesureAir> mesures = mesureAirService.findWithDetailsByTypeAndIndiceLessThan(polluant, maxIndice, pageable);

        return mesures.map(m -> mapper.toDto(m));
    }

    public void sendAlert(DemandeNotification notification) throws RouteParamException, ExternalApiResponseException, ParsedDataException {
        String message = notification.getMessage();
        TypeAlerte alerte = notification.getType();
        String code = notification.getCode();
        validateParams(alerte, code, message);

        List<String> emails = new ArrayList<>();
        switch (alerte) {
            case COMMUNAL -> emails = utilisateurService.getEmailsByCommune(code);
            case DEPARTEMENTAL -> emails = utilisateurService.getEmailsByDepartement(code);
            case REGIONAL -> emails = utilisateurService.getEmailsByRegion(code);
            case NATIONAL -> emails = utilisateurService.getAllEmails();
        }
        mailService.sendEmails(emails, "Alerte qualité de l'air", buildMessage(alerte, code, message));
    }

    public String buildMessage(TypeAlerte alerte, String code, String message) {
        return new StringBuilder().append("Alerte de niveau: ").append(alerte.toString()).append("\n").append(message).toString();
    }
}
