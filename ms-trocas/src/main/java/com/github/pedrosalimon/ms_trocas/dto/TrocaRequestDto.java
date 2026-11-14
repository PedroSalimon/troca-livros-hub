package com.github.pedrosalimon.ms_trocas.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrocaRequestDto {

    @NotNull(message = "O campo usuário solicitante é obrigatório")
    private Long usuarioSolicitanteId;

    @NotNull(message = "O campo usuário destinatário é obrigatório")
    private Long usuarioDestinatarioId;

    @NotNull(message = "O campo livro ofertado é obrigatório")
    private Long livroOfertadoId;

    @NotNull(message = "O campo livro desejado é obrigatório")
    private Long livroDesejadoId;

    @Size(max = 255, message = "O campo mensagem deve ter no máximo 255 caracteres")
    private String mensagem;
}
