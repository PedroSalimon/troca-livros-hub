package com.github.pedrosalimon.ms_usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UsuarioRequestDto {

    @NotBlank(message = "Nome é requerido")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "E-mail é requerido")
    @Email(message = "E-mail inválido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    private String email;

    @NotNull(message = "Idade é requerida")
    @Positive(message = "Idade deve ser um número positivo")
    @Max(value = 120, message = "Idade deve ser no máximo 120")
    private Integer idade;

    @NotBlank(message = "Endereço é requerido")
    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    private String endereco;

    @NotBlank(message = "Cidade é requerida")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "Estado (UF) é requerido")
    @Size(min = 2, max = 2, message = "O estado deve ter 2 caracteres (UF)")
    private String estado;

    private List<@NotBlank(message = "Gênero favorito não pode ser vazio") String> generosFavoritos = new ArrayList<>();

    private List<@NotBlank(message = "Autor favorito não pode ser vazio") String> autoresFavoritos = new ArrayList<>();
}
