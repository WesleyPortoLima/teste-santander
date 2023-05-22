package com.example.demo.controller;

import com.example.demo.model.Transacao;
import com.example.demo.model.dto.ExtratoDTO;
import com.example.demo.model.dto.TransacaoDTO;
import com.example.demo.model.dto.ValorDTO;
import com.example.demo.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    private LocalDate localDate = LocalDate.now();

    @RequestMapping(value = "/conta/{numeroConta}/deposito",method = RequestMethod.POST)
    public ResponseEntity<ExtratoDTO> deposito(
            @PathVariable("numeroConta") String numeroConta,
            @RequestBody ValorDTO valor) {
        ExtratoDTO extrato = transacaoService.depositar(numeroConta, valor.getValor());

        return ResponseEntity.ok(extrato);
    }

    @RequestMapping(value = "/conta/{numeroConta}/saque",method = RequestMethod.POST)
    public ResponseEntity<ExtratoDTO> saque(
            @PathVariable("numeroConta") String numeroConta,
            @RequestBody ValorDTO valor) {
        ExtratoDTO extrato = transacaoService.sacar(numeroConta, valor.getValor());

        return ResponseEntity.ok(extrato);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<Transacao>> findAllTransacaoByData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            TransacaoDTO transacaoDTO) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(transacaoService.findAllTransacaoByData(transacaoDTO, pageable));
    }
}
