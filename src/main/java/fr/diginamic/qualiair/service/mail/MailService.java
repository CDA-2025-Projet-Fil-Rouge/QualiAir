package fr.diginamic.qualiair.service.mail;

import fr.diginamic.qualiair.dto.email.BrevoEmailRequest;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Façade pour les opérations de mailings. L'API actuallement prise en charge est Brevo.
 */
@Service
public class MailService {

    @Autowired
    private BrevoEmailService brevoEmailService;

    /**
     * Fait appel à l'implementation de Brevo pour générer un listing de mails à tous les utilisateurs ciblés
     *
     * @param emails  liste d'e-mails utilisateurs
     * @param subject sujet de l'e-mail
     * @param message contenu de l'e-mail
     * @return objet réponse avec confirmation
     * @throws ExternalApiResponseException Une exception est jetée si la requete vers Brevo a échouée
     */
    public ResponseEntity<BrevoEmailRequest> sendEmails(List<String> emails, String subject, String message) throws ExternalApiResponseException {
        return brevoEmailService.sendEmails(emails, subject, message);
    }

}
