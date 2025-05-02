
package br.com.brasfi.BRASFI.Controller;


import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Service.PostagemService;
import br.com.brasfi.BRASFI.dto.PostagemRequestDTO;
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




    @GetMapping("/postagens")
    public ResponseEntity<List<Postagem>> listarTodasPostagens() {


        List<Postagem> postagens = postagemService.findAll();

            if (postagens.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(postagens);
        }





    @PostMapping("/postagens")
    public ResponseEntity<Postagem> criarPostagem(@RequestBody @Valid PostagemRequestDTO dto) {
        Postagem postagem = dto.toPostagem();
        postagemService.criarPostagem(postagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(postagem);
    }














}
