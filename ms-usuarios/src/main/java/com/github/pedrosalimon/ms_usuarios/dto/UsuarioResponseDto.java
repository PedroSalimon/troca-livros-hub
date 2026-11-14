package com.github.pedrosalimon.ms_usuarios.dto;

import com.github.pedrosalimon.ms_usuarios.entities.Livro;
import com.github.pedrosalimon.ms_usuarios.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UsuarioResponseDto {

    private Long id;
    private String nome;
    private String email;
    private Integer idade;
    private String endereco;
    private String cidade;
    private String estado;
    private LocalDate dataCadastro;

    private List<String> generosFavoritos = new ArrayList<>();
    private List<String> autoresFavoritos = new ArrayList<>();

    private List<LivroResponseDto> livros = new ArrayList<>();

    public UsuarioResponseDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        email = usuario.getEmail();
        idade = usuario.getIdade();
        endereco = usuario.getEndereco();
        cidade = usuario.getCidade();
        estado = usuario.getEstado();
        dataCadastro = usuario.getDataCadastro();

        generosFavoritos.addAll(usuario.getGenerosFavoritos());
        autoresFavoritos.addAll(usuario.getAutoresFavoritos());

        for (Livro livro : usuario.getLivros()) {

            LivroResponseDto livroDTO = new LivroResponseDto(livro);
            livros.add(livroDTO);
        }
    }
}
