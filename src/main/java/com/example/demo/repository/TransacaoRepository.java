package com.example.demo.repository;

import com.example.demo.model.Transacao;
import com.example.demo.model.enums.TipoTransacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransacaoRepository  extends JpaRepository<Transacao, Long> {
    Page<Transacao> findAllTransacaoByDataAndTipoTransacao(LocalDate data, TipoTransacao tipoTransacao, Pageable pageable);
}
