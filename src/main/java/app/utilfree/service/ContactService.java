package app.utilfree.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// ── CONTACT SERVICE ──────────────────────────────────────────────────
@Service
public class ContactService {

    private final JavaMailSender mailSender;

    @Value("${app.contact.recipient}")
    private String recipient;

    @Value("${app.contact.from}")
    private String from;

    public ContactService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String name, String email, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(recipient);
        mail.setReplyTo(email);
        mail.setSubject("[UtilFree Contact] " + subject);
        mail.setText(
            "New contact form submission from UtilFree\n\n" +
            "Name:    " + name    + "\n" +
            "Email:   " + email   + "\n" +
            "Subject: " + subject + "\n\n" +
            "Message:\n" + message + "\n\n" +
            "---\nSent from utilfree.optimaxglobal.com"
        );
        mailSender.send(mail);

        // Auto-reply to sender
        SimpleMailMessage reply = new SimpleMailMessage();
        reply.setFrom(from);
        reply.setTo(email);
        reply.setSubject("We received your message – UtilFree");
        reply.setText(
            "Hi " + name + ",\n\n" +
            "Thanks for reaching out! We've received your message and will reply within 2 business days.\n\n" +
            "— The UtilFree Team\n" +
            "https://utilfree.optimaxglobal.com"
        );
        mailSender.send(reply);
    }
}