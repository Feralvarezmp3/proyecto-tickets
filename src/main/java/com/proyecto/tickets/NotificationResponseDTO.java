package com.proyecto.tickets;

import java.time.LocalDateTime;

public record NotificationResponseDTO(
        String id,
        String tipo,
        String mensaje,
        boolean leida,
        LocalDateTime fecha) {
}
