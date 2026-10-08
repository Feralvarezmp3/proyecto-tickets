package com.proyecto.tickets;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequestDTO(
        @NotBlank String nombre,
        @NotBlank String empresa,
        @NotBlank @Email String correo,
        String telefono,
        String direccion,
        @NotBlank @Size(min = 6, message = "Mínimo 6 caracteres") String contrasena,
        @NotBlank String confirmarContrasena) {
}
