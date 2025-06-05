package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Service.CursoService;
import br.com.brasfi.BRASFI.dto.CriarCursoDTO;
import br.com.brasfi.BRASFI.dto.CursoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> criarCursoCompleto(@RequestBody @Valid CriarCursoDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = cursoService.buscarUsuarioAutenticado(auth.getName());

        CursoResponseDTO response = cursoService.criarCursoCompleto(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> getAllCursos() {
        return ResponseEntity.ok(cursoService.listarTodosCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> getCursoById(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarCursoPorId(id));
    }
}