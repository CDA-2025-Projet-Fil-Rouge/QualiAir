package fr.diginamic.qualiair.service.mail;

import fr.diginamic.qualiair.annotation.DoNotUseDirectly;
import fr.diginamic.qualiair.config.BrevoEmailConfig;
import fr.diginamic.qualiair.dto.email.BrevoEmailRequest;
import fr.diginamic.qualiair.dto.email.EmailBuilder;
import fr.diginamic.qualiair.exception.ExternalApiResponseException;
import fr.diginamic.qualiair.validator.HttpResponseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * Brevo e-mail api implementation, ne pas utiliser directement. Passer par e-mail service à la place.
 */
@DoNotUseDirectly(useInstead = MailServiceImpl.class)
@Service
class BrevoEmailServiceImpl implements BrevoEmailService {

    @Autowired
    private BrevoEmailConfig config;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpResponseValidator validator;

    @Override
    public ResponseEntity<BrevoEmailRequest> sendEmails(List<String> emails, String subject, String message) throws ExternalApiResponseException {
        URI uri = config.getSendEmailUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.set(config.getKeyAttribute(), config.getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        BrevoEmailRequest payload = buildEmails(emails, subject, message);

        HttpEntity<BrevoEmailRequest> request = new HttpEntity<>(payload, headers);
        ResponseEntity<BrevoEmailRequest> response = restTemplate.postForEntity(uri, request, BrevoEmailRequest.class);

        try {
            validator.validate(response);
        } catch (ExternalApiResponseException e) {
            throw new ExternalApiResponseException(String.format("Error sending email listing: %s \n %s", subject, e.getMessage()));
        }
        return response;
    }

    @Override
    public BrevoEmailRequest buildEmails(List<String> emails, String subject, String message) {
        return new EmailBuilder().sender(config.getSenderName(), config.getSenderMail()).receiver(emails).subject(subject).htmlContent(message).build();
    }

}
