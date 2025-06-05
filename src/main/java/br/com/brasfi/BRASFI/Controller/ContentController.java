package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Repository.PostagemRepository;
import br.com.brasfi.BRASFI.dto.PostagemRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    PostagemRepository postagemRepository;

    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }


    @GetMapping("/index")
    public String exibirPaginaIndex(Model model) {
        model.addAttribute("postagemDTO", new PostagemRequestDTO(null, "", "", null,null, null, false));
        model.addAttribute("postagens", postagemRepository.findAll());
        return "index";
    }

}