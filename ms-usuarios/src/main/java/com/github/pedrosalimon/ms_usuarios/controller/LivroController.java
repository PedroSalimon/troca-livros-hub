package com.github.pedrosalimon.ms_usuarios.controller;

import com.github.pedrosalimon.ms_usuarios.dto.LivroRequestDto;
import com.github.pedrosalimon.ms_usuarios.dto.LivroResponseDto;
import com.github.pedrosalimon.ms_usuarios.dto.TrocaConfirmadaRequestDto;
import com.github.pedrosalimon.ms_usuarios.service.LivroService;
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

@Tag(name = "Livros", description = "Livros cadastrados pelos usuários para troca")
@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @Operation(summary = "Lista todos os livros")
    @GetMapping
    public ResponseEntity<List<LivroResponseDto>> getAll(){

        List<LivroResponseDto> list = livroService.findAllLivros();

        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca um livro pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> getLivro(@PathVariable Long id){

        LivroResponseDto livroDto = livroService.findLivroById(id);
        return ResponseEntity.ok(livroDto);
    }

    @Operation(summary = "Cadastra um livro para um usuário")
    @PostMapping
    public ResponseEntity<LivroResponseDto> saveLivro(@RequestBody @Valid
                                                      LivroRequestDto requestDto){

        LivroResponseDto responseDto = livroService.saveLivro(requestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @Operation(summary = "Atualiza um livro (volta a ficar DISPONIVEL)")
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> updateLivro(@PathVariable Long id,
                                                        @Valid @RequestBody LivroRequestDto requestDto){

        LivroResponseDto responseDto = livroService.updateLivro(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Confirma a troca entre dois livros (chamado pelo ms-trocas via OpenFeign)")
    @PutMapping("/troca/confirmada")
    public ResponseEntity<Void> confirmarTroca(@RequestBody @Valid TrocaConfirmadaRequestDto requestDto){

        livroService.confirmarTroca(requestDto);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Exclui um livro")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id){

        livroService.deleteLivroById(id);

        return ResponseEntity.noContent().build();
    }
}
