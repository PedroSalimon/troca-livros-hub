package com.github.pedrosalimon.ms_trocas.controller;

import com.github.pedrosalimon.ms_trocas.dto.TrocaRequestDto;
import com.github.pedrosalimon.ms_trocas.dto.TrocaResponseDto;
import com.github.pedrosalimon.ms_trocas.service.TrocaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/trocas")
public class TrocaController {

    @Autowired
    private TrocaService trocaService;

    @GetMapping
    public ResponseEntity<List<TrocaResponseDto>> getAll(){

        List<TrocaResponseDto> list = trocaService.findAllTrocas();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrocaResponseDto> getTroca(@PathVariable Long id){

        TrocaResponseDto trocaDto = trocaService.findTrocaById(id);

        return ResponseEntity.ok(trocaDto);
    }

    @PostMapping
    public ResponseEntity<TrocaResponseDto> saveTroca(@RequestBody @Valid TrocaRequestDto requestDto){

        TrocaResponseDto responseDto = trocaService.saveTroca(requestDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrocaResponseDto> updateTroca(@PathVariable Long id,
                                                        @Valid @RequestBody TrocaRequestDto requestDto){

        TrocaResponseDto responseDto = trocaService.updateTroca(id, requestDto);

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "confirmarTroca",
            fallbackMethod = "fallbackConfirmarTroca")
    public ResponseEntity<TrocaResponseDto> confirmarTroca(@PathVariable Long id){

        TrocaResponseDto dto = trocaService.confirmarTroca(id);

        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<TrocaResponseDto> fallbackConfirmarTroca(Long id, Throwable e){

        log.error("Falha ao confirmar troca {}. Ativando fallback. Erro: {}", id, e.getMessage());

        TrocaResponseDto dto = trocaService.alterarStatusDaTroca(id);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTroca(@PathVariable Long id){

        trocaService.deleteTrocaById(id);

        return ResponseEntity.noContent().build();
    }
}
