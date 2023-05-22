package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.model.Transacao;
import com.example.demo.model.dto.ExtratoDTO;
import com.example.demo.model.dto.TransacaoDTO;
import com.example.demo.model.enums.TipoTransacao;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.TransacaoRepository;
import com.example.demo.service.exception.ObjectNotFoundException;
import com.example.demo.service.exception.ValorInvalidoException;
import com.example.demo.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransacaoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public ExtratoDTO depositar(String conta, BigDecimal valor) {
        Cliente cliente = clienteRepository.findClienteByNumeroConta(conta);

        if (cliente == null) {
            throw new ObjectNotFoundException("Conta não encontrada");
        } else {
            BigDecimal saldoFinal = cliente.getSaldo().add(valor);

            Cliente clienteAtualizado = clienteService.alteraSaldo(cliente, saldoFinal);

            registraTransacao(conta, valor, TipoTransacao.DEPOSITO);

            return new ExtratoDTO(
                    clienteAtualizado.getNome(),
                    clienteAtualizado.getNumeroConta(),
                    clienteAtualizado.getSaldo(),
                    LocalDateTime.now());
        }
    }

    public ExtratoDTO sacar(String conta, BigDecimal valor) {
        Cliente cliente = clienteRepository.findClienteByNumeroConta(conta);

        if (cliente == null) {
            throw new ObjectNotFoundException("Conta não encontrada");
        } else {
            if (cliente.getSaldo().compareTo(valor) == -1) {
                throw new ValorInvalidoException("Saldo insuficiente para realizar a operação");
            } else {
                BigDecimal valorTaxa = calculaValorTaxaSaque(valor);

                BigDecimal saldoFinal = valor.subtract(valorTaxa);

                Cliente clienteAtualizado = clienteService.alteraSaldo(cliente, saldoFinal);

                registraTransacao(conta, valor, TipoTransacao.SAQUE);

                return new ExtratoDTO(
                        clienteAtualizado.getNome(),
                        clienteAtualizado.getNumeroConta(),
                        clienteAtualizado.getSaldo(),
                        LocalDateTime.now());
            }
        }
    }

    public BigDecimal calculaValorTaxaSaque(BigDecimal valor) {
        BigDecimal taxa;
        if (valor.compareTo(BigDecimalUtil.CEM) <= 0) {
            taxa = BigDecimal.ZERO;
        } else if (valor.compareTo(BigDecimalUtil.CEM) == 1 && valor.compareTo(BigDecimalUtil.TREZENTOS) <= 0) {
            taxa = valor.multiply(BigDecimalUtil.PERCENTUAL_04);
        } else {
            taxa = valor.multiply(BigDecimalUtil.PERCENTUAL_1);
        }

        return taxa;
    }

    public void registraTransacao(String conta, BigDecimal valor, TipoTransacao tipoTransacao) {
        Transacao transacao = new Transacao(tipoTransacao, LocalDate.now(), valor, conta);

        transacaoRepository.save(transacao);
    }

    public Page<Transacao> findAllTransacaoByData(TransacaoDTO transacaoDTO, Pageable pageable) {
        return transacaoRepository.findAllTransacaoByDataAndTipoTransacao(transacaoDTO.getData(), transacaoDTO.getTipoTransacao(), pageable);
    }
}
