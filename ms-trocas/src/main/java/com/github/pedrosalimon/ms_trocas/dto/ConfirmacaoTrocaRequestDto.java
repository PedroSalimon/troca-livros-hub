package com.github.pedrosalimon.ms_trocas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConfirmacaoTrocaRequestDto {

    private Long livroOfertadoId;
    private Long livroDesejadoId;
}
