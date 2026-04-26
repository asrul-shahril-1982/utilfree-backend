package app.utilfree.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
    @NotBlank @Size(min=2, max=100)  String name,
    @NotBlank @Email                  String email,
    @NotBlank @Size(min=3, max=150)  String subject,
    @NotBlank @Size(min=10, max=2000)String message
) {}