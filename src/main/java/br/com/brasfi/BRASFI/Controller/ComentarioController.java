package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.Comentario;
import br.com.brasfi.BRASFI.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

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

}
