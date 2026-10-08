package com.proyecto.tickets;

public record ClientResponseDTO(
        String id,
        String nombre,
        String empresa,
        String correo,
        String telefono,
        String direccion) {
}
