
package br.com.brasfi.BRASFI.Controller;


import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.Service.PostagemService;
import br.com.brasfi.BRASFI.dto.PostagemRequestDTO;
import br.com.brasfi.BRASFI.dto.PostagemResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostagemController {

    @Autowired
    PostagemService postagemService;

    @Autowired
    UserRepository userRepository;




    @GetMapping("/postagens")
    public ResponseEntity<List<PostagemResponseDTO>> listarTodasPostagens() {
        List<Postagem> postagens = postagemService.findAll();

        if (postagens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PostagemResponseDTO> resposta = postagens.stream()
                .map(PostagemResponseDTO::new)
                .toList();

        return ResponseEntity.ok(resposta);
    }





    @PostMapping("/postagens")

    public ResponseEntity<Postagem> criarPostagem(@RequestBody @Valid PostagemRequestDTO dto) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Postagem postagem = dto.toPostagem(user);


        postagem.setUser(user);

        postagemService.criarPostagem(postagem);

        System.out.println("AUTENTICADO COMO: " + user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(postagem);
    }


    @GetMapping("/postagens/tag/{tag}")
    public ResponseEntity<List<PostagemResponseDTO>> listarTodasPostagensTag(@PathVariable TipoPostagem tag) {
        List<Postagem> postagens = postagemService.listarPostagensPorTag(tag);

        if (postagens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PostagemResponseDTO> resposta = postagens.stream()
                .map(PostagemResponseDTO::new)
                .toList();

        return ResponseEntity.ok(resposta);
    }


    @GetMapping("/postagens/fixadas")
    public ResponseEntity<List<PostagemResponseDTO>> listarTodasPostagensFixadas() {
        List<Postagem> postagens = postagemService.listarPostagensFixadas();

        if (postagens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PostagemResponseDTO> resposta = postagens.stream()
                .map(PostagemResponseDTO::new)
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/postagens/fixar/{id}")
    public ResponseEntity<?> fixarPostagem(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acesso negado: apenas administradores podem fixar postagens.");
        }

        postagemService.fixarPostagem(id);
        return ResponseEntity.ok("Postagem fixada com sucesso.");
    }

    @PostMapping("/postagens/desfixar/{id}")
    public ResponseEntity<?> desfixarPostagem(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (user.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Acesso negado: apenas administradores podem desfixar postagens.");
        }

        postagemService.desfixarPostagem(id);
        return ResponseEntity.ok("Postagem desfixada com sucesso.");
    }


    @PutMapping("/postagens/{id}")
    public ResponseEntity<Postagem> editarPostagem(@PathVariable Long id, @RequestBody @Valid PostagemRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));


        Postagem postagemExistente = postagemService.buscarPorId(id);
        if (postagemExistente == null) {
            throw new EntityNotFoundException("Postagem não encontrada");
        }


        if (!postagemExistente.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        Postagem novaPostagem = dto.toPostagem(user);
        novaPostagem.setId(id);
        novaPostagem.setUser(user);

        postagemService.editarPostagem(id, novaPostagem);

        return ResponseEntity.ok(novaPostagem);
    }


    @DeleteMapping("/postagens/{id}")
    public ResponseEntity<Void> deletarPostagem(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Postagem postagem = postagemService.buscarPorId(id);
        if (postagem == null) {
            return ResponseEntity.notFound().build();
        }

        if (!postagem.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        postagemService.deletarPostagem(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/postagens/{id}")
    public ResponseEntity<Postagem> buscarPostagemPorId(@PathVariable Long id) {
        Postagem postagem = postagemService.buscarPorId(id);
        return ResponseEntity.ok(postagem);
    }

    @GetMapping("/auth/check")
    public ResponseEntity<Map<String, String>> checkAuth(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário autenticado com sucesso!");

        response.put("username", user.getUsername());
        response.put("photo", user.getPhoto());
        response.put("role", user.getRole().name());

        return ResponseEntity.ok(response);
    }
























}
