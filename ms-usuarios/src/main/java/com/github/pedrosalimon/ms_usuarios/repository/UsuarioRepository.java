package com.github.pedrosalimon.ms_usuarios.repository;

import com.github.pedrosalimon.ms_usuarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);
}
