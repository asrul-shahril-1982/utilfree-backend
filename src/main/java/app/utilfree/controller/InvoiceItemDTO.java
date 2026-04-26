package app.utilfree.controller;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record InvoiceItemDTO(
    @NotBlank String description,
    int quantity,
    BigDecimal unitPrice
) {}