package app.utilfree.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// ── REPOSITORY ──────────────────────────────────────────────────────
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClientEmailOrderByCreatedAtDesc(String clientEmail);
    List<Invoice> findByBusinessEmailOrderByCreatedAtDesc(String businessEmail);
}