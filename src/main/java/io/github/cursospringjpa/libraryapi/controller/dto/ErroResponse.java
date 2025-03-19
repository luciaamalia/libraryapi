package io.github.cursospringjpa.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponse(int status, String message, List<ErroCampo> erros) {

    public static ErroResponse respostaPadrao(String message){
        return new ErroResponse(HttpStatus.BAD_REQUEST.value(),message, List.of());
    }

    public static ErroResponse conflito(String message){
        return new ErroResponse(HttpStatus.CONFLICT.value(), message, List.of());
    }
}
