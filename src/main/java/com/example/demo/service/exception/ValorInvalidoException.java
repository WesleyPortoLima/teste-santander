package com.example.demo.service.exception;

public class ValorInvalidoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ValorInvalidoException(String msg) {
        super(msg);
    }

    public ValorInvalidoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

