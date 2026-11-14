package com.github.pedrosalimon.ms_usuarios.dto;

import com.github.pedrosalimon.ms_usuarios.entities.CondicaoLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LivroRequestDto {

    @NotBlank(message = "Autor é requerido")
    @Size(max = 100, message = "O autor deve ter no máximo 100 caracteres")
    private String autor;

    @NotBlank(message = "Título é requerido")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
    private String titulo;

    @NotBlank(message = "Gênero é requerido")
    @Size(max = 100, message = "O gênero deve ter no máximo 100 caracteres")
    private String genero;

    @NotBlank(message = "Editora é requerida")
    @Size(max = 100, message = "A editora deve ter no máximo 100 caracteres")
    private String editora;

    @NotNull(message = "Condição do livro é requerida (NOVO, SEMINOVO, BOM, REGULAR ou DESGASTADO)")
    private CondicaoLivro condicao;

    @NotNull(message = "Usuário (dono do livro) é requerido")
    private Long usuarioId;
}
