package com.github.pedrosalimon.ms_trocas.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FieldMessageDto {

    private String fieldName;
    private String message;

}
