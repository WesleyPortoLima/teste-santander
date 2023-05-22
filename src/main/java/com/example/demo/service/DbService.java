package com.example.demo.service;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void instantiateTestDatabase() throws ParseException {
        List<Cliente> list = new ArrayList<>();

        Cliente cli1 = new Cliente();
        cli1.setNome("Cliente Teste");
        cli1.setSaldo(new BigDecimal("100"));
        cli1.setExclusive(false);
        cli1.setDataNascimento(LocalDate.now());
        cli1.setNumeroConta("12345");
        list.add(cli1);


        Cliente cli2 = new Cliente();
        cli2.setNome("Cliente Teste 2");
        cli2.setSaldo(new BigDecimal("50"));
        cli2.setExclusive(true);
        cli2.setDataNascimento(LocalDate.now());
        cli2.setNumeroConta("123456");
        list.add(cli2);

        clienteRepository.saveAll(list);

    }
}
