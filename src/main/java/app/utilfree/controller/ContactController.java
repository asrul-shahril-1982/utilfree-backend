package app.utilfree.controller;

import app.utilfree.service.ContactService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

record ContactRequest(
    @NotBlank @Size(min=2, max=100)  String name,
    @NotBlank @Email                  String email,
    @NotBlank @Size(min=3, max=150)  String subject,
    @NotBlank @Size(min=10, max=2000)String message
) {}

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * POST /api/contact
     * Sends an email to admin and an auto-reply to the sender.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> contact(@Valid @RequestBody ContactRequest req) {
        try {
            contactService.send(req.name(), req.email(), req.subject(), req.message());
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Your message has been sent. We'll reply within 2 business days."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "success", false,
                "message", "Failed to send message. Please try emailing us directly at privacy@utilfree.optimaxglobal.com"
            ));
        }
    }
}
