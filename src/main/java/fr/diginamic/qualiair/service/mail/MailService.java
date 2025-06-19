package fr.diginamic.qualiair.service.mail;

import fr.diginamic.qualiair.dto.email.BrevoEmailRequest;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private BrevoEmailService brevoEmailService;

    public ResponseEntity<BrevoEmailRequest> sendEmails(List<String> emails, String subject, String message) throws ExternalApiResponseException {
        return brevoEmailService.sendEmails(emails, subject, message);
    }

}
