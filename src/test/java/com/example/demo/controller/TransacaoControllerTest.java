package com.example.demo.controller;

import com.example.demo.model.dto.ExtratoDTO;
import com.example.demo.repository.TransacaoRepository;
import com.example.demo.service.DbService;
import com.example.demo.service.TransacaoService;
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

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TransacaoService transacaoService;

    @MockBean
    TransacaoRepository transacaoRepository;

    @MockBean
    DbService dbService;

    @Test
    public void shouldFindAllTransacao() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("page", "0");
        multiValueMap.add("size", "5");

        mockMvc.perform(MockMvcRequestBuilders.get("/transacoes")
                        .queryParams(multiValueMap)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeposita() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        ExtratoDTO extratoDTO = DomainUtils.extratoDTO();

        when(transacaoService.depositar(anyString(), any(BigDecimal.class))).thenReturn(extratoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/conta/12345/deposito")
                        .content(mapper.writeValueAsString(DomainUtils.valorDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSaque() throws Exception {
        mapper.registerModule(new JavaTimeModule());
        ExtratoDTO extratoDTO = DomainUtils.extratoDTO();

        when(transacaoService.sacar(anyString(), any(BigDecimal.class))).thenReturn(extratoDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/transacoes/conta/12345/saque")
                        .content(mapper.writeValueAsString(DomainUtils.valorDTO()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
