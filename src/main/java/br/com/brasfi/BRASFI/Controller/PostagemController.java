
package br.com.brasfi.BRASFI.Controller;


import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        Postagem postagem = dto.toPostagem();

        // Obtém o usuário autenticado - SUBSTITUA ISSO no futuro com Spring Security
        User user = userRepository.findById(1L) // <-- mock por enquanto
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        postagem.setUser(user);

        postagemService.criarPostagem(postagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(postagem);
    }














}
