package app.utilfree.controller;

import app.utilfree.exception.ResourceNotFoundException;
import app.utilfree.service.InvoiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// ── DTO ─────────────────────────────────────────────────────────────
record InvoiceItemDTO(
    @NotBlank String description,
    int quantity,
    BigDecimal unitPrice
) {}

record SaveInvoiceRequest(
    @NotBlank String invoiceNumber,
    @NotBlank String businessName,
    @Email    String businessEmail,
    @NotBlank String clientName,
    @Email    String clientEmail,
              String clientAddress,
    @NotBlank String currency,
              BigDecimal taxRate,
              LocalDate invoiceDate,
              LocalDate dueDate,
              String notes,
              List<InvoiceItemDTO> items
) {}

// ── CONTROLLER ──────────────────────────────────────────────────────
@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /**
     * POST /api/invoice/save
     * Save an invoice record to the database.
     */
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody SaveInvoiceRequest req) {
        Long id = invoiceService.save(req);
        return ResponseEntity.ok(Map.of(
            "success", true,
            "invoiceId", id,
            "message", "Invoice saved successfully"
        ));
    }

    /**
     * GET /api/invoice/list?email=client@example.com
     * Retrieve invoices by client email.
     */
    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam @Email String email) {
        return ResponseEntity.ok(invoiceService.findByClientEmail(email));
    }

    /**
     * GET /api/invoice/{id}
     * Retrieve a single invoice by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return invoiceService.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
    }

    /**
     * DELETE /api/invoice/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.ok(Map.of("success", true));
    }
}
