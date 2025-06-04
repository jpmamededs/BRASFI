package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Service.ComentarioService;
import br.com.brasfi.BRASFI.dto.ComentarioRequestDTO;
import br.com.brasfi.BRASFI.dto.ComentarioResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;



    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criarComentario(@RequestBody @Valid ComentarioRequestDTO dto) {
        try {
            ComentarioResponseDTO response = comentarioService.criarComentario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listarTodos() {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarTodosComentarios();
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/postagem/{postagemId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarPorPostagem(@PathVariable Long postagemId) {
        try {
            List<ComentarioResponseDTO> comentarios = comentarioService.listarComentariosPorPostagem(postagemId);
            return ResponseEntity.ok(comentarios);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{comentarioId}")
    public ResponseEntity<ComentarioResponseDTO> editarComentario(
            @PathVariable Long comentarioId,
            @RequestBody @Valid ComentarioRequestDTO novoDTO
    ) {
        try {
            ComentarioResponseDTO response = comentarioService.editarComentario(comentarioId, novoDTO);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Long comentarioId) {
        try {
            comentarioService.deletarComentario(comentarioId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}