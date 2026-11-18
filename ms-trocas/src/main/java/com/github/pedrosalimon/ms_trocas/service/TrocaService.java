package com.github.pedrosalimon.ms_trocas.service;

import com.github.pedrosalimon.ms_trocas.client.UsuarioClient;
import com.github.pedrosalimon.ms_trocas.dto.ConfirmacaoTrocaRequestDto;
import com.github.pedrosalimon.ms_trocas.dto.TrocaRequestDto;
import com.github.pedrosalimon.ms_trocas.dto.TrocaResponseDto;
import com.github.pedrosalimon.ms_trocas.entities.Status;
import com.github.pedrosalimon.ms_trocas.entities.Troca;
import com.github.pedrosalimon.ms_trocas.exceptions.ResourceNotFoundException;
import com.github.pedrosalimon.ms_trocas.exceptions.TrocaConcluidaException;
import com.github.pedrosalimon.ms_trocas.exceptions.TrocaInvalidaException;
import com.github.pedrosalimon.ms_trocas.repository.TrocaRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrocaService {

    @Autowired
    private TrocaRepository trocaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Transactional(readOnly = true)
    public List<TrocaResponseDto> findAllTrocas(){

        return trocaRepository.findAll()
                .stream()
                .map(TrocaResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public TrocaResponseDto findTrocaById(Long id){

        Troca troca = buscarTroca(id);
        return new TrocaResponseDto(troca);
    }

    @Transactional
    public TrocaResponseDto saveTroca(TrocaRequestDto requestDto){

        validarTroca(requestDto);

        Troca troca = new Troca();
        troca.setDataProposta(LocalDate.now());
        troca.setStatus(Status.PROPOSTA);
        mapDtoToTroca(requestDto, troca);
        troca = trocaRepository.save(troca);
        return new TrocaResponseDto(troca);
    }

    @Transactional
    public TrocaResponseDto updateTroca(Long id, TrocaRequestDto requestDto){

        Troca troca = buscarTroca(id);

        if (troca.getStatus().equals(Status.CONCLUIDA)){
            throw new TrocaConcluidaException(
                    String.format("Troca id: %d já está CONCLUIDA e não pode ser alterada.", id)
            );
        }

        validarTroca(requestDto);

        troca.setStatus(Status.PROPOSTA);
        mapDtoToTroca(requestDto, troca);
        troca = trocaRepository.save(troca);
        return new TrocaResponseDto(troca);
    }

    @Transactional
    public void deleteTrocaById(Long id){

        if(!trocaRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        trocaRepository.deleteById(id);
    }

    @Transactional
    public TrocaResponseDto confirmarTroca(Long id){

        Troca troca = buscarTroca(id);

        if (troca.getStatus().equals(Status.CONCLUIDA)){
            throw new TrocaConcluidaException(
                    String.format("Troca id: %d já está CONCLUIDA.", id)
            );
        }

        try {
            usuarioClient.confirmarTroca(new ConfirmacaoTrocaRequestDto(
                    troca.getLivroOfertadoId(), troca.getLivroDesejadoId()));
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Livro da troca não encontrado. ID da troca: " + id);
        } catch (FeignException e) {
            throw new RuntimeException("Falha ao se comunicar com ms-usuarios");
        }

        troca.setStatus(Status.CONCLUIDA);
        troca = trocaRepository.save(troca);
        return new TrocaResponseDto(troca);
    }

    @Transactional
    public TrocaResponseDto alterarStatusDaTroca(Long id){

        Troca troca = buscarTroca(id);

        if (troca.getStatus().equals(Status.CONCLUIDA)){
            throw new TrocaConcluidaException(
                    String.format("Troca id: %d já está CONCLUIDA.", id)
            );
        }

        troca.setStatus(Status.CONFIRMACAO_PENDENTE);
        troca = trocaRepository.save(troca);
        return new TrocaResponseDto(troca);
    }

    private Troca buscarTroca(Long id) {

        return trocaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );
    }

    private void validarTroca(TrocaRequestDto requestDto) {

        if (requestDto.getUsuarioSolicitanteId().equals(requestDto.getUsuarioDestinatarioId())) {
            throw new TrocaInvalidaException("Solicitante e destinatário devem ser usuários diferentes.");
        }
        if (requestDto.getLivroOfertadoId().equals(requestDto.getLivroDesejadoId())) {
            throw new TrocaInvalidaException("Livro ofertado e livro desejado devem ser diferentes.");
        }
    }

    private void mapDtoToTroca(TrocaRequestDto requestDto, Troca troca) {

        troca.setUsuarioSolicitanteId(requestDto.getUsuarioSolicitanteId());
        troca.setUsuarioDestinatarioId(requestDto.getUsuarioDestinatarioId());
        troca.setLivroOfertadoId(requestDto.getLivroOfertadoId());
        troca.setLivroDesejadoId(requestDto.getLivroDesejadoId());
        troca.setMensagem(requestDto.getMensagem());
    }
}
