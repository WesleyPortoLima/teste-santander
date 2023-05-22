package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastraNovoCliente(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    public Page<Cliente> findAllClientes(Pageable pageable) {

        return clienteRepository.findAll(pageable);
    }

    public Cliente alteraSaldo(Cliente cliente, BigDecimal valor) {
        cliente.setSaldo(valor);
        return clienteRepository.save(cliente);
    }

    public Cliente fromDTO(ClienteDTO dto) {
        return new Cliente(dto.getNome(), dto.isExclusive(), dto.getSaldo(),dto.getNumeroConta(), dto.getDataNascimento());
    }
}
