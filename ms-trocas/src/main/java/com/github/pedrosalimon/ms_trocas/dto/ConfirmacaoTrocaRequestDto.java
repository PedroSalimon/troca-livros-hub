package com.github.pedrosalimon.ms_trocas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Contrato enviado ao ms-usuarios (via OpenFeign) para confirmar a troca dos livros.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConfirmacaoTrocaRequestDto {

    private Long livroOfertadoId;
    private Long livroDesejadoId;
}
