package com.proyecto.tickets;

import java.time.LocalDate;

public record TicketUpdateDTO(
        String estado,
        String urgencia,
        String prioridad,
        LocalDate fechaEstimadaSolucion,
        String grupoAsignacion,
        String asignadoA) {
}
