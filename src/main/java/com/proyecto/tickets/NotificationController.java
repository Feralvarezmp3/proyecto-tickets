package com.proyecto.tickets;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<NotificationResponseDTO> findAll(@RequestParam String usuarioId) {
        return service.findByDestinatario(usuarioId);
    }

    @PatchMapping("/leidas")
    public void markAllAsRead(@RequestParam String usuarioId) {
        service.markAllAsRead(usuarioId);
    }
}
