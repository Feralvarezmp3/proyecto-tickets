package com.proyecto.tickets;

import java.util.List;
import java.util.Map;

public record ReportResponseDTO(
        long total,
        Map<String, Long> ticketsPorEstado,
        Map<String, Long> ticketsPorUrgencia,
        List<TicketResponseDTO> tickets) {
}
