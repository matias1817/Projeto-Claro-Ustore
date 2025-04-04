package com.claro.projeto.util.execeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaExeception  extends RuntimeException {

    public EntidadeNaoEncontradaExeception(String entidade, Long id) {
        super(String.format("Não foi encontrado(a) um(a) %s com o id %s", entidade, id.toString()));
    }
}