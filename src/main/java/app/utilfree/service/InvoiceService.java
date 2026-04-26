package app.utilfree.service;

import app.utilfree.controller.InvoiceItemDTO;
import app.utilfree.controller.SaveInvoiceRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// ── INVOICE SERVICE ──────────────────────────────────────────────────
@Service
public class InvoiceService {

    private final InvoiceRepository repo;

    public InvoiceService(InvoiceRepository repo) {
        this.repo = repo;
    }

    public Long save(SaveInvoiceRequest req) {
        Invoice inv = new Invoice();
        inv.setInvoiceNumber(req.invoiceNumber());
        inv.setBusinessName(req.businessName());
        inv.setBusinessEmail(req.businessEmail());
        inv.setClientName(req.clientName());
        inv.setClientEmail(req.clientEmail());
        inv.setClientAddress(req.clientAddress());
        inv.setCurrency(req.currency());
        inv.setTaxRate(req.taxRate() != null ? req.taxRate() : BigDecimal.ZERO);
        inv.setInvoiceDate(req.invoiceDate());
        inv.setDueDate(req.dueDate());
        inv.setNotes(req.notes());

        // Calculate totals
        if (req.items() != null) {
            BigDecimal sub = req.items().stream()
                .map(i -> i.unitPrice().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            inv.setSubtotal(sub);
            BigDecimal taxAmt = sub.multiply(inv.getTaxRate()).divide(BigDecimal.valueOf(100));
            inv.setTotalAmount(sub.add(taxAmt));

            // Serialize items to simple JSON string
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < req.items().size(); i++) {
                InvoiceItemDTO item = req.items().get(i);
                sb.append(String.format(
                    "{\"description\":\"%s\",\"quantity\":%d,\"unitPrice\":%s}",
                    item.description().replace("\"","\\\""),
                    item.quantity(),
                    item.unitPrice().toPlainString()
                ));
                if (i < req.items().size() - 1) sb.append(",");
            }
            sb.append("]");
            inv.setItemsJson(sb.toString());
        }

        return repo.save(inv).getId();
    }

    public List<Invoice> findByClientEmail(String email) {
        return repo.findByClientEmailOrderByCreatedAtDesc(email);
    }

    public Optional<Invoice> findById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}