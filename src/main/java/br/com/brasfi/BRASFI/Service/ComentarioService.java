package br.com.brasfi.BRASFI.Service;

import br.com.brasfi.BRASFI.Model.Comentario;
import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Repository.ComentarioRepository;
import br.com.brasfi.BRASFI.Repository.PostagemRepository;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.dto.ComentarioRequestDTO;
import br.com.brasfi.BRASFI.dto.ComentarioResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ComentarioResponseDTO criarComentario(ComentarioRequestDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Postagem postagem = postagemRepository.findById(dto.postagemId())
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));

        Comentario comentario = dto.toComentario(postagem, user);
        comentario = comentarioRepository.save(comentario);

        return new ComentarioResponseDTO(comentario);
    }

    @Transactional(readOnly = true)
    public List<ComentarioResponseDTO> listarTodosComentarios() {
        List<Comentario> comentarios = comentarioRepository.findAll();
        return comentarios.stream()
                .map(ComentarioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ComentarioResponseDTO> listarComentariosPorPostagem(Long postagemId) {
        Postagem postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));


        return postagem.getComentarios().stream()
                .map(ComentarioResponseDTO::new)
                .toList();
    }

    @Transactional
    public ComentarioResponseDTO editarComentario(Long comentarioId, ComentarioRequestDTO novoDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));


        if (!comentario.getUser().getUsername().equals(username)) {

            throw new SecurityException("Usuário não tem permissão para editar este comentário.");
        }


        comentario.setTitulo(novoDTO.titulo());
        comentario.setConteudo(novoDTO.conteudo());
        comentario.setDataCriacao(novoDTO.dataCriacao());

        comentario = comentarioRepository.save(comentario);
        return new ComentarioResponseDTO(comentario);
    }

    @Transactional
    public void deletarComentario(Long comentarioId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));


        if (!comentario.getUser().getUsername().equals(username)) {
            throw new SecurityException("Usuário não tem permissão para deletar este comentário.");
        }


        Postagem postagem = comentario.getPostagem();
        if (postagem != null && postagem.getComentarios().contains(comentario)) {

            postagem.getComentarios().remove(comentario);
        }

        comentarioRepository.delete(comentario);
    }
}