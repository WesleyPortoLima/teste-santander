package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<Void> cadastraCliente(@Valid @RequestBody ClienteDTO cliente) {
        Cliente objCliente = clienteService.fromDTO(cliente);
        Cliente newCliente = clienteService.cadastraNovoCliente(objCliente);

        URI uri = fromCurrentRequest()
                .path("/{id}").buildAndExpand(newCliente.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Cliente>> findAllClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(clienteService.findAllClientes(pageable));
    }
}
