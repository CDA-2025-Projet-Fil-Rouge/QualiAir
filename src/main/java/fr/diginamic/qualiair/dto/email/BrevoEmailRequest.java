package fr.diginamic.qualiair.dto.email;

import fr.diginamic.qualiair.annotation.DoNotUseDirectly;

import java.util.List;

@DoNotUseDirectly(useInstead = EmailBuilder.class)
public class BrevoEmailRequest {
    private Sender sender;
    private List<Receiver> receivers;
    private String subject;
    private String htmlContent;

    BrevoEmailRequest() {
    }

    /**
     * Getter
     *
     * @return sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * Setter
     *
     * @param sender sets value
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * Getter
     *
     * @return receivers
     */
    public List<Receiver> getReceivers() {
        return receivers;
    }

    /**
     * Setter
     *
     * @param receivers sets value
     */
    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    /**
     * Getter
     *
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Setter
     *
     * @param subject sets value
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Getter
     *
     * @return htmlContent
     */
    public String getHtmlContent() {

        return htmlContent;
    }

    /**
     * Setter
     *
     * @param htmlContent sets value
     */
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
