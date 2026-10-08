package com.proyecto.tickets;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TicketResponseDTO(
        String id,
        String numeroSolicitud,
        String numeroReporte,
        String personaUsuaria,
        String generadoPor,
        String urgencia,
        String prioridad,
        String estado,
        String descripcionBreve,
        String detalles,
        String requerimientoServicio,
        LocalDateTime fechaCreacion,
        LocalDate fechaEstimadaSolucion,
        String grupoAsignacion,
        String asignadoA) {
}
