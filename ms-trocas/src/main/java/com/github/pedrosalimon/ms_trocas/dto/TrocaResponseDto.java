package com.github.pedrosalimon.ms_trocas.dto;

import com.github.pedrosalimon.ms_trocas.entities.Status;
import com.github.pedrosalimon.ms_trocas.entities.Troca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrocaResponseDto {

    private Long id;
    private Long usuarioSolicitanteId;
    private Long usuarioDestinatarioId;
    private Long livroOfertadoId;
    private Long livroDesejadoId;
    private String mensagem;
    private LocalDate dataProposta;
    private Status status;

    public TrocaResponseDto(Troca troca) {
        id = troca.getId();
        usuarioSolicitanteId = troca.getUsuarioSolicitanteId();
        usuarioDestinatarioId = troca.getUsuarioDestinatarioId();
        livroOfertadoId = troca.getLivroOfertadoId();
        livroDesejadoId = troca.getLivroDesejadoId();
        mensagem = troca.getMensagem();
        dataProposta = troca.getDataProposta();
        status = troca.getStatus();
    }
}
