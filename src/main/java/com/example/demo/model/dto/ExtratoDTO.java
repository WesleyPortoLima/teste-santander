package com.example.demo.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExtratoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;

    private String conta;

    private BigDecimal saldo;

    private LocalDateTime data;

    public ExtratoDTO() {}

    public ExtratoDTO(String nome, String conta, BigDecimal saldo, LocalDateTime data) {
        this.nome = nome;
        this.conta = conta;
        this.saldo = saldo;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
