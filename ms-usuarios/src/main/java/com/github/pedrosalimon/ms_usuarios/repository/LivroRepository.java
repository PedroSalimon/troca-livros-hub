package com.github.pedrosalimon.ms_usuarios.repository;

import com.github.pedrosalimon.ms_usuarios.entities.Livro;
import com.github.pedrosalimon.ms_usuarios.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l JOIN FETCH l.usuario u "
            + "WHERE l.status = :status "
            + "AND u.id <> :usuarioId "
            + "AND LOWER(u.cidade) = LOWER(:cidade) "
            + "AND LOWER(u.estado) = LOWER(:estado)")
    List<Livro> findDisponiveisProximos(@Param("status") Status status,
                                        @Param("usuarioId") Long usuarioId,
                                        @Param("cidade") String cidade,
                                        @Param("estado") String estado);
}
