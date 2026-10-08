package com.proyecto.tickets;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReportController {

    private final ReportService reportService;
    private final PdfReportService pdfService;
    private final EmailService emailService;

    public ReportController(ReportService reportService, PdfReportService pdfService, EmailService emailService) {
        this.reportService = reportService;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    @GetMapping
    public ReportResponseDTO generar(
            @RequestParam(required = false) String urgencia,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFinal,
            @RequestParam(required = false) String asignadoA) {
        return reportService.generar(urgencia, fechaInicial, fechaFinal, asignadoA);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> pdf(
            @RequestParam(required = false) String urgencia,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFinal,
            @RequestParam(required = false) String asignadoA) {
        byte[] archivo = pdfService.generate(reportService.filtrar(urgencia, fechaInicial, fechaFinal, asignadoA));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-tickets.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(archivo);
    }

    @PostMapping("/enviar")
    public void enviarPorCorreo(@RequestParam String correo) {
        ReportResponseDTO reporte = reportService.generar(null, null, null, null);
        emailService.send(correo, "Reporte de tickets",
                "Total de tickets: " + reporte.total() + "\nPor estado: " + reporte.ticketsPorEstado());
    }
}
