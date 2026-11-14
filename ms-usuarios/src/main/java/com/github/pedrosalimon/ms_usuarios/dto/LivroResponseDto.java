package com.github.pedrosalimon.ms_usuarios.dto;

import com.github.pedrosalimon.ms_usuarios.entities.CondicaoLivro;
import com.github.pedrosalimon.ms_usuarios.entities.Livro;
import com.github.pedrosalimon.ms_usuarios.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LivroResponseDto {

    private Long id;
    private String autor;
    private String titulo;
    private String genero;
    private String editora;
    private CondicaoLivro condicao;
    private Status status;
    private Long usuarioId;

    public LivroResponseDto(Livro livro) {
        id = livro.getId();
        autor = livro.getAutor();
        titulo = livro.getTitulo();
        genero = livro.getGenero();
        editora = livro.getEditora();
        condicao = livro.getCondicao();
        status = livro.getStatus();
        usuarioId = livro.getUsuario().getId();
    }
}
