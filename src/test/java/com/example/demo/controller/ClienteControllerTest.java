package com.example.demo.controller;

import com.example.demo.model.Cliente;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.service.ClienteService;
import com.example.demo.service.DbService;
import com.example.demo.utils.DomainUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    ClienteService clienteService;

    @MockBean
    ClienteRepository clienteRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @Test
    public void shouldCadastraCliente() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        Cliente cliente = DomainUtils.clienteSemId();
        ClienteDTO clienteDTO = DomainUtils.clienteDTO();

        when(clienteService.fromDTO(any())).thenReturn(cliente);
        when(clienteService.cadastraNovoCliente(any())).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .content(mapper.writeValueAsString(clienteDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldThrowExceptionCadastraCliente() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        Cliente cliente = DomainUtils.clienteSemId();
        ClienteDTO clienteDTO = DomainUtils.clienteDTOWithoutName();

        when(clienteService.fromDTO(any())).thenReturn(cliente);
        when(clienteService.cadastraNovoCliente(any())).thenReturn(cliente);

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                        .content(mapper.writeValueAsString(clienteDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindAllClientes() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("page", "0");
        multiValueMap.add("size", "5");

        mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .queryParams(multiValueMap)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
