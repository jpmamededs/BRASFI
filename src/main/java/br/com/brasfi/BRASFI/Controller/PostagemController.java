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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostagemController {

    @Autowired
    private PostagemService postagemService;

    @Autowired
    private UserRepository userRepository;

    // Feed com formulário
    @GetMapping("/feed")
    public String mostrarFeed(Model model) {
        model.addAttribute("postagemDTO", new PostagemRequestDTO());
        model.addAttribute("postagens", postagemService.findAll());
        return "feed";
    }

    // Envio do formulário HTML (form post)
    @PostMapping("/postagens")
    public String criarPostagem(@ModelAttribute("postagemDTO") @Valid PostagemRequestDTO dto) {
        Postagem postagem = dto.toPostagem();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        postagem.setUser(user);
        postagem.setAutor(user.getUsername());

        postagemService.criarPostagem(postagem);
        return "redirect:/feed";
    }

    // JSON API endpoint
    @GetMapping("/postagens")
    @ResponseBody
    public ResponseEntity<List<PostagemResponseDTO>> listarTodasPostagens() {
        List<Postagem> postagens = postagemService.findAll();
        if (postagens.isEmpty()) return ResponseEntity.noContent().build();

        List<PostagemResponseDTO> resposta = postagens.stream().map(PostagemResponseDTO::new).toList();
        return ResponseEntity.ok(resposta);
    }
}
