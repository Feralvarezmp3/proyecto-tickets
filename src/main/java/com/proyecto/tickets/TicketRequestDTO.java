package com.proyecto.tickets;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record TicketRequestDTO(
        @NotBlank String descripcionBreve,
        @NotBlank String detalles,
        String requerimientoServicio,
        @NotBlank String personaUsuaria,
        String generadoPor,
        @NotBlank String urgencia,
        LocalDate fechaEstimadaSolucion,
        String grupoAsignacion,
        String asignadoA) {
}
