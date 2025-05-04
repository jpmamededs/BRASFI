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

    @GetMapping("/{postagemId}")
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Long postagemId){
        return ResponseEntity.ok().body(List.of());
    }

    @PostMapping("/postar")
    public ResponseEntity<Comentario> criarComentario(
            @RequestParam Long postagemId,
            @RequestBody Comentario comentario,
            @RequestParam User user
    )
    {
        return ResponseEntity.ok(comentario);
    }

    @PatchMapping("/{postagemId}/editar")
    public ResponseEntity<Comentario> editarComentario(
            @PathVariable Long postagemId,
            @RequestBody Comentario novoComentario,
            @RequestParam User user
    )
    {
        return ResponseEntity.ok(novoComentario);
    }

    @DeleteMapping("/{postagemId}/deletar")
    public ResponseEntity<Void> deletarComentario(@PathVariable Long postagemId, User user){
        return ResponseEntity.noContent().build();
    }

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




}
