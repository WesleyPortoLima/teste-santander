package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.utils.DomainUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    @Test
    public void shouldSave() {
        Cliente cliComId = DomainUtils.clienteComId();

        when(clienteRepository.save(ArgumentMatchers.any())).thenReturn(cliComId);
        when(clienteService.cadastraNovoCliente(ArgumentMatchers.any())).thenReturn(cliComId);

        Cliente cliente = clienteService.cadastraNovoCliente(DomainUtils.clienteSemId());

        verify(clienteRepository, times(1)).save(ArgumentMatchers.any());

        assertEquals(cliente, cliComId);
    }

    @Test
    public void shouldFindAllClientes() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Cliente> page = mock(Page.class);

        when(clienteRepository.findAll(pageable)).thenReturn(page);
        when(clienteService.findAllClientes(pageable)).thenReturn(page);

        Page<Cliente> cliente = clienteService.findAllClientes(pageable);

        verify(clienteRepository, times(1)).findAll(pageable);

        assertEquals(cliente, page);
    }

    @Test
    public void shouldAlteraSaldo() {
        Cliente cliComId = DomainUtils.clienteComId();

        when(clienteRepository.save(ArgumentMatchers.any())).thenReturn(cliComId);
        when(clienteService.alteraSaldo(cliComId, any())).thenReturn(cliComId);

        Cliente cliente = clienteService.alteraSaldo(DomainUtils.clienteSemId(), BigDecimal.TEN);

        verify(clienteRepository, times(1)).save(ArgumentMatchers.any());

        assertEquals(cliente, cliComId);
    }
}
