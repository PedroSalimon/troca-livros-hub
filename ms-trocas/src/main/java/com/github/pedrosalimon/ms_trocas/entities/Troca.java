package com.github.pedrosalimon.ms_trocas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_troca")
public class Troca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencias por ID: cada microsservico tem o seu proprio banco
    @Column(nullable = false)
    private Long usuarioSolicitanteId;

    @Column(nullable = false)
    private Long usuarioDestinatarioId;

    @Column(nullable = false)
    private Long livroOfertadoId;

    @Column(nullable = false)
    private Long livroDesejadoId;

    private String mensagem;

    @Column(nullable = false)
    private LocalDate dataProposta;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
