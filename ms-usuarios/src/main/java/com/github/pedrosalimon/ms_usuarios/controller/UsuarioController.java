package com.github.pedrosalimon.ms_usuarios.controller;

import com.github.pedrosalimon.ms_usuarios.dto.LivroResponseDto;
import com.github.pedrosalimon.ms_usuarios.dto.UsuarioRequestDto;
import com.github.pedrosalimon.ms_usuarios.dto.UsuarioResponseDto;
import com.github.pedrosalimon.ms_usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Usuários", description = "Cadastro de usuários e recomendação de livros")
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Lista todos os usuários")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){

        List<UsuarioResponseDto> list = usuarioService.findAllUsuarios();

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getUsuario(@PathVariable Long id){

        UsuarioResponseDto usuarioDto = usuarioService.findUsuarioById(id);
        return ResponseEntity.ok(usuarioDto);
    }

    @Operation(summary = "Lista livros disponíveis na cidade do usuário que combinam com seus gêneros ou autores favoritos")
    @GetMapping("/{id}/livros-recomendados")
    public ResponseEntity<List<LivroResponseDto>> getLivrosRecomendados(@PathVariable Long id){

        List<LivroResponseDto> list = usuarioService.findLivrosRecomendados(id);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Cadastra um novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> saveUsuario(@RequestBody @Valid
                                                          UsuarioRequestDto requestDto){

        UsuarioResponseDto responseDto = usuarioService.saveUsuario(requestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @Operation(summary = "Atualiza um usuário")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updateUsuario(@PathVariable Long id,
                                                            @Valid @RequestBody UsuarioRequestDto requestDto){

        UsuarioResponseDto responseDto = usuarioService.updateUsuario(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Exclui um usuário (e seus livros)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){

        usuarioService.deleteUsuarioById(id);

        return ResponseEntity.noContent().build();
    }
}
