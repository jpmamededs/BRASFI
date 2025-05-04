
package br.com.brasfi.BRASFI.Controller;


import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/usuarios")
    public List<User> listarUsuarios() {
        return userRepository.findAll();
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














}
