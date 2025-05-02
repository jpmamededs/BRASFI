package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Service.PostagemService;
import br.com.brasfi.BRASFI.dto.PostagemRequestDTO;
import br.com.brasfi.BRASFI.dto.PostagemResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ContentController {

    @GetMapping("/req/login")
    public String login() {
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }
}
