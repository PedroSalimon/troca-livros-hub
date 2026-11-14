package com.github.pedrosalimon.ms_usuarios.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    private LocalDate dataCadastro;

    @ElementCollection
    @CollectionTable(name = "tb_usuario_generos_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "genero", nullable = false, length = 100)
    private List<String> generosFavoritos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tb_usuario_autores_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "autor", nullable = false, length = 100)
    private List<String> autoresFavoritos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros = new ArrayList<>();
}
