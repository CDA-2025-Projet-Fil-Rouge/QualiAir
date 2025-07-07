package fr.diginamic.qualiair.service.mail;

import fr.diginamic.qualiair.dto.email.BrevoEmailRequest;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Façade pour les opérations de mailings. L'API actuellement prise en charge est Brevo.
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private BrevoEmailService brevoEmailService;

    @Override
    public ResponseEntity<BrevoEmailRequest> sendEmails(List<String> emails, String subject, String message) throws ExternalApiResponseException {
        return brevoEmailService.sendEmails(emails, subject, message);
    }

}
