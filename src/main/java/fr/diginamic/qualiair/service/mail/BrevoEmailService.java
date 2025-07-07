package fr.diginamic.qualiair.service.mail;

import fr.diginamic.qualiair.dto.email.BrevoEmailRequest;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.http.ResponseEntity;

import java.util.List;

interface BrevoEmailService {
    /**
     * Construit et Envoie un e-mail à un listing d'utilisateurs
     *
     * @param emails  liste d'e-mails d'utilisateurs
     * @param subject sujet de l'email
     * @param message message de l'email au format string
     * @return la reponse http de l'email
     * @throws ExternalApiResponseException en cas de status autre que 200 une erreur est jetée
     */
    ResponseEntity<BrevoEmailRequest> sendEmails(List<String> emails, String subject, String message) throws ExternalApiResponseException;

    /**
     * Construit un e-mail pour un listing d'utilisateurs
     *
     * @param emails  liste d'e-mails d'utilisateurs
     * @param subject sujet de l'email
     * @param message message de l'email au format string
     * @return dto de l'email
     */
    BrevoEmailRequest buildEmails(List<String> emails, String subject, String message);
}
