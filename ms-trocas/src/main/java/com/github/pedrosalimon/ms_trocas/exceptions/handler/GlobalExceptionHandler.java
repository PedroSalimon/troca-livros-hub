package com.github.pedrosalimon.ms_trocas.exceptions.handler;

import com.github.pedrosalimon.ms_trocas.exceptions.DatabaseException;
import com.github.pedrosalimon.ms_trocas.exceptions.LivroIndisponivelException;
import com.github.pedrosalimon.ms_trocas.exceptions.ResourceNotFoundException;
import com.github.pedrosalimon.ms_trocas.exceptions.TrocaConcluidaException;
import com.github.pedrosalimon.ms_trocas.exceptions.TrocaInvalidaException;
import com.github.pedrosalimon.ms_trocas.exceptions.dto.CustomErrorDto;
import com.github.pedrosalimon.ms_trocas.exceptions.dto.ValidationErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - recurso não encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDto> handleResourceNotFound(ResourceNotFoundException e,
                                                                 HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; //404
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 409 - troca já concluída não pode ser alterada
    @ExceptionHandler(TrocaConcluidaException.class)
    public ResponseEntity<CustomErrorDto> handleTrocaConcluida(TrocaConcluidaException e,
                                                               HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT; //409
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 409 - livro indisponível (informado pelo ms-usuarios)
    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<CustomErrorDto> handleLivroIndisponivel(LivroIndisponivelException e,
                                                                  HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT; //409
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 400 - proposta de troca inválida (ex.: mesmo usuário nos dois lados)
    @ExceptionHandler(TrocaInvalidaException.class)
    public ResponseEntity<CustomErrorDto> handleTrocaInvalida(TrocaInvalidaException e,
                                                              HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; //400
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 409 - conflito de dados
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomErrorDto> handleDatabase(DatabaseException e,
                                                         HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT; //409
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 422 - dados inválidos (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDto> methodArgumentNotValid(MethodArgumentNotValidException e,
                                                                 HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; //422
        ValidationErrorDto err = new ValidationErrorDto(Instant.now(), status.value(),
                "Dados inválidos", request.getRequestURI());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }

    // 400 - JSON malformado / corpo inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorDto> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                       HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; //400
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                "Requisição inválida (JSON malformado ou corpo não interpretável).",
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 400 - tipo inválido em PathVariable/RequestParam
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorDto> handleTypeMismatch(MethodArgumentTypeMismatchException e,
                                                             HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; //400
        CustomErrorDto err = new CustomErrorDto(Instant.now(), status.value(),
                "Requisição inválida (parâmetro com tipo/formato incorreto).",
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
