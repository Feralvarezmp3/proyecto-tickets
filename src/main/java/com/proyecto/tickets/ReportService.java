package com.proyecto.tickets;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final TicketRepository repository;
    private final TicketService ticketService;

    public ReportService(TicketRepository repository, TicketService ticketService) {
        this.repository = repository;
        this.ticketService = ticketService;
    }

    public List<Ticket> filtrar(String urgencia, LocalDate fechaInicial, LocalDate fechaFinal, String asignadoA) {
        return repository.findAll().stream()
                .filter(t -> urgencia == null || urgencia.isBlank() || urgencia.equalsIgnoreCase(t.getUrgencia()))
                .filter(t -> asignadoA == null || asignadoA.isBlank() || asignadoA.equalsIgnoreCase(t.getAsignadoA()))
                .filter(t -> fechaInicial == null || !t.getFechaCreacion().toLocalDate().isBefore(fechaInicial))
                .filter(t -> fechaFinal == null || !t.getFechaCreacion().toLocalDate().isAfter(fechaFinal))
                .toList();
    }

    public ReportResponseDTO generar(String urgencia, LocalDate fechaInicial, LocalDate fechaFinal, String asignadoA) {
        List<Ticket> tickets = filtrar(urgencia, fechaInicial, fechaFinal, asignadoA);
        Map<String, Long> porEstado = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getEstado, Collectors.counting()));
        Map<String, Long> porUrgencia = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getUrgencia, Collectors.counting()));
        return new ReportResponseDTO(tickets.size(), porEstado, porUrgencia,
                tickets.stream().map(ticketService::toDto).toList());
    }
}
