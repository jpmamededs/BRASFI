package br.com.brasfi.BRASFI.Controller;



import br.com.brasfi.BRASFI.Model.Curso;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Repository.CursoRepository;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.Service.CursoService;
import br.com.brasfi.BRASFI.dto.CriarCursoDTO;
import br.com.brasfi.BRASFI.dto.CursoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {


    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<CursoResponseDTO> criarCursoCompleto(@RequestBody @Valid CriarCursoDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        System.out.println("AUTENTICADO COMO: " + user.getUsername());


        CursoResponseDTO response = cursoService.criarCursoCompleto(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> getAllCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        List<CursoResponseDTO> cursosDTO = cursos.stream()
                .map(CursoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(cursosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> getCursoById(@PathVariable Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);

        if (cursoOptional.isPresent()) {
            CursoResponseDTO cursoDTO = CursoResponseDTO.fromEntity(cursoOptional.get());
            return ResponseEntity.ok(cursoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}