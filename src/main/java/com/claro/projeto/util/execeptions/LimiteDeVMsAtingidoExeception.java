package com.claro.projeto.util.execeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class LimiteDeVMsAtingidoExeception extends RuntimeException {

    public LimiteDeVMsAtingidoExeception( String name) {
        super(String.format("O limite de maquinas virtuais do usuario foi atingido", name));
    }
}