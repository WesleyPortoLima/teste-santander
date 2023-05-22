package com.example.demo.model.enums;
public enum TipoTransacao {

    SAQUE(1, "Saque"),
    DEPOSITO(2, "Deposito");

    private int cod;
    private String descricao;

    private TipoTransacao(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoTransacao toEnum(Integer codigo) {

        if (codigo == null) {
            return null;
        }

        for (TipoTransacao x : TipoTransacao.values()) {
            if(codigo.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido" + codigo);
    }
}
