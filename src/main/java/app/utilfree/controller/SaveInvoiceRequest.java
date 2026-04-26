package app.utilfree.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record SaveInvoiceRequest(
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