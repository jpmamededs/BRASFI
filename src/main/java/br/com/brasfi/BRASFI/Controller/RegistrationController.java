package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.DTO.UserDTO;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public User createUser(@RequestBody UserDTO userDTO) {
        User user = new User();

        // Obrigatórios
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUserName());

        // Opcionais
        user.setMidleName(userDTO.getMidleName());
        user.setBiografia(userDTO.getBiografia());
        user.setLocalizacao(userDTO.getLocalizacao());
        if (userDTO.getIdade() != null) user.setIdade(userDTO.getIdade());
        user.setPhoto(userDTO.getPhoto());
        user.setLinkInstagram(userDTO.getLinkInstagram());
        user.setLinkLinkedin(userDTO.getLinkLinkedin());
        user.setLinkLattes(userDTO.getLinkLattes());
        user.setLinkWhatsapp(userDTO.getLinkWhatsapp());

        if (userDTO.getTemasDeAtuacao() != null) user.setTemasDeAtuacao(userDTO.getTemasDeAtuacao());
        if (userDTO.getProfissao() != null) user.setProfissao(userDTO.getProfissao());

        user.setGenero(userDTO.getGenero());
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : Role.USER); // padrão

        return userRepository.save(user);
    }
}
