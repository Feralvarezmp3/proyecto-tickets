package com.proyecto.tickets;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponseDTO create(@Valid @RequestBody TicketRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<TicketResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TicketResponseDTO findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/solicitud/{numero}")
    public TicketResponseDTO findByNumero(@PathVariable String numero) {
        return service.findByNumero(numero);
    }

    @PutMapping("/{id}")
    public TicketResponseDTO update(@PathVariable String id, @RequestBody TicketUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
