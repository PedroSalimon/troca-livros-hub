package com.github.pedrosalimon.ms_usuarios.exceptions.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto extends CustomErrorDto {

    private List<FieldMessageDto> errors = new ArrayList<>();

    public ValidationErrorDto(Instant timestamp, Integer status,
                              String error, String path) {

        super(timestamp, status, error, path);
    }

    // metodo para adicionar erros à List
    public void addError(String fieldName, String message){
        // remove erro de campo duplicado
        errors.removeIf(x -> x.getFieldName().equals(fieldName));
        errors.add(new FieldMessageDto(fieldName, message));
    }
}
