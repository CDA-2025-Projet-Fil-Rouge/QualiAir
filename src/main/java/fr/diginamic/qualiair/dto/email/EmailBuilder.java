package fr.diginamic.qualiair.dto.email;

public class EmailBuilder {
    private BrevoEmailRequest request;

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

    public EmailBuilder content(String htmlContent) {
        request.setHtmlContent(htmlContent);
        return this;
    }

    public BrevoEmailRequest build() {
        return request;
    }
}
