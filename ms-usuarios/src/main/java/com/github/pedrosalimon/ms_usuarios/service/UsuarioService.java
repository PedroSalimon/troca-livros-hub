package com.github.pedrosalimon.ms_usuarios.service;

import com.github.pedrosalimon.ms_usuarios.dto.LivroResponseDto;
import com.github.pedrosalimon.ms_usuarios.dto.UsuarioRequestDto;
import com.github.pedrosalimon.ms_usuarios.dto.UsuarioResponseDto;
import com.github.pedrosalimon.ms_usuarios.entities.Status;
import com.github.pedrosalimon.ms_usuarios.entities.Usuario;
import com.github.pedrosalimon.ms_usuarios.exceptions.DatabaseException;
import com.github.pedrosalimon.ms_usuarios.exceptions.ResourceNotFoundException;
import com.github.pedrosalimon.ms_usuarios.repository.LivroRepository;
import com.github.pedrosalimon.ms_usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> findAllUsuarios(){

        return usuarioRepository.findAll()
                .stream().map(UsuarioResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDto findUsuarioById(Long id){

        Usuario usuario = buscarUsuario(id);
        return new UsuarioResponseDto(usuario);
    }

    @Transactional
    public UsuarioResponseDto saveUsuario(UsuarioRequestDto requestDto){

        if (usuarioRepository.existsByEmail(requestDto.getEmail())){
            throw new DatabaseException("E-mail já cadastrado: " + requestDto.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setDataCadastro(LocalDate.now());
        mapDtoToUsuario(requestDto, usuario);
        usuario = usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuario);
    }

    @Transactional
    public UsuarioResponseDto updateUsuario(Long id, UsuarioRequestDto requestDto){

        Usuario usuario = buscarUsuario(id);

        if (usuarioRepository.existsByEmailAndIdNot(requestDto.getEmail(), id)){
            throw new DatabaseException("E-mail já cadastrado: " + requestDto.getEmail());
        }

        mapDtoToUsuario(requestDto, usuario);
        usuario = usuarioRepository.save(usuario);
        return new UsuarioResponseDto(usuario);
    }

    @Transactional
    public void deleteUsuarioById(Long id){

        if(!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LivroResponseDto> findLivrosRecomendados(Long id){

        Usuario usuario = buscarUsuario(id);

        Set<String> generos = normalizar(usuario.getGenerosFavoritos());
        Set<String> autores = normalizar(usuario.getAutoresFavoritos());

        return livroRepository.findDisponiveisProximos(Status.DISPONIVEL, usuario.getId(),
                        usuario.getCidade(), usuario.getEstado())
                .stream()
                .filter(l -> generos.contains(l.getGenero().trim().toLowerCase())
                        || autores.contains(l.getAutor().trim().toLowerCase()))
                .map(LivroResponseDto::new)
                .toList();
    }

    private Usuario buscarUsuario(Long id) {

        return usuarioRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );
    }

    private Set<String> normalizar(List<String> valores) {

        return valores.stream()
                .map(v -> v.trim().toLowerCase())
                .collect(Collectors.toSet());
    }

    private void mapDtoToUsuario(UsuarioRequestDto requestDto, Usuario usuario) {

        usuario.setNome(requestDto.getNome());
        usuario.setEmail(requestDto.getEmail());
        usuario.setIdade(requestDto.getIdade());
        usuario.setEndereco(requestDto.getEndereco());
        usuario.setCidade(requestDto.getCidade());
        usuario.setEstado(requestDto.getEstado().toUpperCase());

        usuario.getGenerosFavoritos().clear();
        usuario.getGenerosFavoritos().addAll(requestDto.getGenerosFavoritos());

        usuario.getAutoresFavoritos().clear();
        usuario.getAutoresFavoritos().addAll(requestDto.getAutoresFavoritos());
    }
}
