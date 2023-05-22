package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Transacao;
import com.example.demo.model.enums.TipoTransacao;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.TransacaoRepository;
import com.example.demo.service.exception.ValorInvalidoException;
import com.example.demo.util.BigDecimalUtil;
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
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ClienteService clienteService;

    @Mock
    TransacaoRepository transacaoRepository;

    @InjectMocks
    TransacaoService transacaoService;

    @Test
    public void shouldDepositar() {
        Cliente cliComId = DomainUtils.clienteComId();
        when(clienteRepository.findClienteByNumeroConta(anyString())).thenReturn(cliComId);
        when(clienteService.alteraSaldo(any(), any())).thenReturn(cliComId);

        transacaoService.depositar("12345", BigDecimal.ONE);

        verify(transacaoRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldSacar() {
        Cliente cliComId = DomainUtils.clienteComId();
        when(clienteRepository.findClienteByNumeroConta(anyString())).thenReturn(cliComId);
        when(clienteService.alteraSaldo(any(), any())).thenReturn(cliComId);

        transacaoService.sacar("12345", BigDecimal.ONE);

        verify(transacaoRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldThrowValorInvalidoExceptionWhenValorMaiorQueSaldo() {
        Cliente cliComId = DomainUtils.clienteComId();
        when(clienteRepository.findClienteByNumeroConta(anyString())).thenReturn(cliComId);

        Exception exception = assertThrows(ValorInvalidoException.class, () -> {
            transacaoService.sacar("12345", BigDecimalUtil.TREZENTOS);
        });
        String expectedMessage = "Saldo insuficiente para realizar a operação";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldFindAllTransacao() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Transacao> page = mock(Page.class);

        when(transacaoRepository.findAllTransacaoByDataAndTipoTransacao(LocalDate.now(), TipoTransacao.DEPOSITO, pageable)).thenReturn(page);
        when(transacaoService.findAllTransacaoByData(DomainUtils.transacaoDTO() ,pageable)).thenReturn(page);

        Page<Transacao> pageResult = transacaoService.findAllTransacaoByData(DomainUtils.transacaoDTO(), pageable);

        verify(transacaoRepository, times(1)).findAllTransacaoByDataAndTipoTransacao(LocalDate.now(), TipoTransacao.DEPOSITO, pageable);

        assertEquals(pageResult, page);
    }

    @Test
    public void shouldRegistraTransacao() {
        transacaoService.registraTransacao("12345", BigDecimal.ONE, TipoTransacao.DEPOSITO);
        verify(transacaoRepository, times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void shouldCalculaTaxa() {
        BigDecimal taxaSaque = transacaoService.calculaValorTaxaSaque(BigDecimalUtil.TREZENTOS);
        BigDecimal result = new BigDecimal("1.200");

        assertEquals(taxaSaque, result);
    }
}
