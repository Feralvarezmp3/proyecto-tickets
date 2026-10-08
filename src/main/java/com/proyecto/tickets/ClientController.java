package com.proyecto.tickets;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientResponseDTO register(@Valid @RequestBody ClientRequestDTO dto) {
        return service.register(dto);
    }

    @GetMapping
    public List<ClientResponseDTO> findAll(@RequestParam(required = false) String buscar) {
        return service.findAll(buscar);
    }

    @GetMapping("/{id}")
    public ClientResponseDTO findById(@PathVariable String id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
