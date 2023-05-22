package com.example.demo.utils;

import com.example.demo.model.Cliente;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ExtratoDTO;
import com.example.demo.model.dto.TransacaoDTO;
import com.example.demo.model.dto.ValorDTO;
import com.example.demo.model.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DomainUtils {

    public static Cliente clienteComId() {
        Cliente cli1 = new Cliente();

        cli1.setId(1L);
        cli1.setNome("Cliente Teste");
        cli1.setSaldo(new BigDecimal("100"));
        cli1.setExclusive(false);
        cli1.setDataNascimento(LocalDate.now());
        cli1.setNumeroConta("12345");

        return cli1;
    }

    public static Cliente clienteSemId() {
        Cliente cli1 = new Cliente();

        cli1.setNome("Cliente Teste");
        cli1.setSaldo(new BigDecimal("100"));
        cli1.setExclusive(false);
        cli1.setDataNascimento(LocalDate.now());
        cli1.setNumeroConta("12345");

        return cli1;
    }

    public static ClienteDTO clienteDTO() {
        ClienteDTO cli1 = new ClienteDTO();

        cli1.setNome("Cliente Teste");
        cli1.setSaldo(new BigDecimal("100"));
        cli1.setExclusive(false);
        cli1.setDataNascimento(LocalDate.now());
        cli1.setNumeroConta("12345");

        return cli1;
    }

    public static ClienteDTO clienteDTOWithoutName() {
        ClienteDTO cli1 = new ClienteDTO();

        cli1.setNome("");
        cli1.setSaldo(new BigDecimal("100"));
        cli1.setExclusive(false);
        cli1.setDataNascimento(LocalDate.now());
        cli1.setNumeroConta("12345");

        return cli1;
    }

    public static TransacaoDTO transacaoDTO() {
        TransacaoDTO transacao = new TransacaoDTO();

        transacao.setTipoTransacao(TipoTransacao.DEPOSITO);
        transacao.setData(LocalDate.now());

        return transacao;
    }

    public static ExtratoDTO extratoDTO() {
        ExtratoDTO extratoDTO = new ExtratoDTO();

        extratoDTO.setData(LocalDateTime.now());
        extratoDTO.setNome("Teste");
        extratoDTO.setSaldo(BigDecimal.TEN);
        extratoDTO.setConta("12345");

        return extratoDTO;
    }

    public static ValorDTO valorDTO() {
        ValorDTO valorDTO = new ValorDTO();

        valorDTO.setValor(BigDecimal.TEN);

        return valorDTO;
    }
}
