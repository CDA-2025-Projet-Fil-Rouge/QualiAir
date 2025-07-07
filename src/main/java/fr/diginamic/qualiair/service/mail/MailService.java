package fr.diginamic.qualiair.service.mail;

import fr.diginamic.qualiair.dto.email.BrevoEmailRequest;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MailService {
    /**
     * Fait appel à l'implementation de Brevo pour générer un listing de mails à tous les utilisateurs ciblés
     *
     * @param emails  liste d'e-mails utilisateurs
     * @param subject sujet de l'e-mail
     * @param message contenu de l'e-mail
     * @return objet réponse avec confirmation
     * @throws ExternalApiResponseException Une exception est jetée si la requete vers Brevo a échouée
     */
    ResponseEntity<BrevoEmailRequest> sendEmails(List<String> emails, String subject, String message) throws ExternalApiResponseException;
}
