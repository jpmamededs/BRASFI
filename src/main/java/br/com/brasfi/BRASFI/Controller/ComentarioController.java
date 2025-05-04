package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.Comentario;
import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Repository.ComentarioRepository;
import br.com.brasfi.BRASFI.Repository.PostagemRepository;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.dto.ComentarioRequestDTO;
import br.com.brasfi.BRASFI.dto.ComentarioResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criarComentario(@RequestBody @Valid ComentarioRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Postagem postagem = postagemRepository.findById(dto.postagemId())
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));

        Comentario comentario = dto.toComentario(postagem, user);
        comentarioRepository.save(comentario);

        return ResponseEntity.ok(new ComentarioResponseDTO(comentario));
    }

    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listarTodos() {
        List<Comentario> comentarios = comentarioRepository.findAll();
        List<ComentarioResponseDTO> resposta = comentarios.stream()
                .map(ComentarioResponseDTO::new)
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarPorPostagem(@PathVariable Long postagemId) {
        Postagem postagem = postagemRepository.findById(postagemId)
                .orElseThrow(() -> new EntityNotFoundException("Postagem não encontrada"));

        List<ComentarioResponseDTO> comentarios = postagem.getComentarios().stream()
                .map(ComentarioResponseDTO::new)
                .toList();

        return ResponseEntity.ok(comentarios);
    }

    @PutMapping("/{comentarioId}")
    public ResponseEntity<ComentarioResponseDTO> editarComentario(
            @PathVariable Long comentarioId,
            @RequestBody @Valid ComentarioRequestDTO novoDTO
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));

        if (!comentario.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        comentario.setTitulo(novoDTO.titulo());
        comentario.setConteudo(novoDTO.conteudo());
        comentario.setDataCriacao(novoDTO.dataCriacao());

        comentarioRepository.save(comentario);
        return ResponseEntity.ok(new ComentarioResponseDTO(comentario));
    }

    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Long comentarioId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));

        if (!comentario.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Desvincula da postagem
        Postagem postagem = comentario.getPostagem();
        if (postagem != null) {
            postagem.getComentarios().remove(comentario);
        }

        comentarioRepository.delete(comentario);
        return ResponseEntity.noContent().build();
    }


}
