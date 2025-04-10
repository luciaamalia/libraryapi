package io.github.cursospringjpa.libraryapi.controller.common;

import io.github.cursospringjpa.libraryapi.controller.dto.ErroCampo;
import io.github.cursospringjpa.libraryapi.controller.dto.ErroResponse;
import io.github.cursospringjpa.libraryapi.exceptions.CampoInvalidoException;
import io.github.cursospringjpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursospringjpa.libraryapi.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //captura o erro
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(),fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                " Erro de validação",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse handleRegistroDuplicadoException(RegistroDuplicadoException e){
       return ErroResponse.conflito(e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResponse.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(CampoInvalidoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handleCampoInvalidoException(CampoInvalidoException e ){
        return new ErroResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponse handleErrosNaoTratados(RuntimeException e){
        return new ErroResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro inesperado. Entre em contato com a administração",
                List.of());
    }
}
