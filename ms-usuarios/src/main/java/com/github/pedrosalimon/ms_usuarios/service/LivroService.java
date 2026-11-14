package com.github.pedrosalimon.ms_usuarios.service;

import com.github.pedrosalimon.ms_usuarios.dto.LivroRequestDto;
import com.github.pedrosalimon.ms_usuarios.dto.LivroResponseDto;
import com.github.pedrosalimon.ms_usuarios.dto.TrocaConfirmadaRequestDto;
import com.github.pedrosalimon.ms_usuarios.entities.Livro;
import com.github.pedrosalimon.ms_usuarios.entities.Status;
import com.github.pedrosalimon.ms_usuarios.entities.Usuario;
import com.github.pedrosalimon.ms_usuarios.exceptions.LivroIndisponivelException;
import com.github.pedrosalimon.ms_usuarios.exceptions.ResourceNotFoundException;
import com.github.pedrosalimon.ms_usuarios.repository.LivroRepository;
import com.github.pedrosalimon.ms_usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<LivroResponseDto> findAllLivros(){

        return livroRepository.findAll()
                .stream().map(LivroResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public LivroResponseDto findLivroById(Long id){

        Livro livro = buscarLivro(id);
        return new LivroResponseDto(livro);
    }

    @Transactional
    public LivroResponseDto saveLivro(LivroRequestDto requestDto){

        Livro livro = new Livro();
        mapDtoToLivro(requestDto, livro);
        livro.setStatus(Status.DISPONIVEL);
        livro = livroRepository.save(livro);
        return new LivroResponseDto(livro);
    }

    @Transactional
    public LivroResponseDto updateLivro(Long id, LivroRequestDto requestDto){

        Livro livro = buscarLivro(id);
        mapDtoToLivro(requestDto, livro);
        livro.setStatus(Status.DISPONIVEL);
        livro = livroRepository.save(livro);
        return new LivroResponseDto(livro);
    }

    @Transactional
    public void deleteLivroById(Long id){

        if(!livroRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        livroRepository.deleteById(id);
    }

    // Chamado pelo ms-trocas (OpenFeign): altera o status dos dois livros para TROCADO
    // e passa cada livro para o novo dono.
    @Transactional
    public void confirmarTroca(TrocaConfirmadaRequestDto requestDto){

        Livro ofertado = buscarLivro(requestDto.getLivroOfertadoId());
        Livro desejado = buscarLivro(requestDto.getLivroDesejadoId());

        if (ofertado.getStatus() != Status.DISPONIVEL || desejado.getStatus() != Status.DISPONIVEL){
            throw new LivroIndisponivelException(String.format(
                    "Livros %d e %d precisam estar DISPONIVEIS para a troca.",
                    ofertado.getId(), desejado.getId()));
        }

        Usuario donoDoOfertado = ofertado.getUsuario();
        ofertado.setUsuario(desejado.getUsuario());
        desejado.setUsuario(donoDoOfertado);

        ofertado.setStatus(Status.TROCADO);
        desejado.setStatus(Status.TROCADO);

        livroRepository.save(ofertado);
        livroRepository.save(desejado);
    }

    private Livro buscarLivro(Long id) {

        return livroRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Livro não encontrado. ID: " + id)
        );
    }

    private void mapDtoToLivro(LivroRequestDto requestDto, Livro livro) {

        Usuario usuario = usuarioRepository.findById(requestDto.getUsuarioId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuário não encontrado. ID: " + requestDto.getUsuarioId())
        );

        livro.setAutor(requestDto.getAutor());
        livro.setTitulo(requestDto.getTitulo());
        livro.setGenero(requestDto.getGenero());
        livro.setEditora(requestDto.getEditora());
        livro.setCondicao(requestDto.getCondicao());
        livro.setUsuario(usuario);
    }
}
