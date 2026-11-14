package com.github.pedrosalimon.ms_usuarios.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Contrato recebido do ms-trocas (via OpenFeign) quando uma troca é confirmada.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrocaConfirmadaRequestDto {

    @NotNull(message = "Livro ofertado é requerido")
    private Long livroOfertadoId;

    @NotNull(message = "Livro desejado é requerido")
    private Long livroDesejadoId;
}
