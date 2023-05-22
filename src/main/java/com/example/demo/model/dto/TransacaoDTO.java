package com.example.demo.model.dto;

import com.example.demo.model.enums.TipoTransacao;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class TransacaoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data;

    @NotNull
    private TipoTransacao tipoTransacao;

    public TransacaoDTO() {
    }

    public TransacaoDTO(LocalDate data, TipoTransacao tipoTransacao) {
        this.data = data;
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
}
