package fr.diginamic.qualiair.dto.email;

import java.util.List;

public class EmailBuilder {
    private final BrevoEmailRequest request;

    public EmailBuilder() {
        this.request = new BrevoEmailRequest();
    }

    public EmailBuilder sender(String name, String email) {
        request.setSender(new Sender(name, email));
        return this;
    }

    public EmailBuilder receiver(String email) {
        request.getReceivers().add(new Receiver(email));
        return this;
    }

    public EmailBuilder receiver(List<String> emails) {

        request.getReceivers().addAll(emails.stream().map(Receiver::new).toList());
        return this;
    }

    public EmailBuilder subject(String subject) {
        request.setSubject(subject);
        return this;
    }

    public EmailBuilder htmlContent(String htmlContent) {
        String content = new StringBuilder().append("<html><body>").append(htmlContent).append("</body></html>").toString();
        request.setHtmlContent(content);
        return this;
    }

    public BrevoEmailRequest build() {
        return request;
    }
}
