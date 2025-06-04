package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TemaAtuacao;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    @GetMapping("/usuarios/me")
    public ResponseEntity<User> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {

        User user = userDTO.toUser();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        if (!requester.getId().equals(id) && requester.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        user.setBiografia(updatedUser.getBiografia());
        user.setLocalizacao(updatedUser.getLocalizacao());
        user.setIdade(updatedUser.getIdade());
        user.setPhoto(updatedUser.getPhoto());
        user.setLinkInstagram(updatedUser.getLinkInstagram());
        user.setLinkLinkedin(updatedUser.getLinkLinkedin());
        user.setLinkLattes(updatedUser.getLinkLattes());
        user.setLinkWhatsapp(updatedUser.getLinkWhatsapp());
        user.setTemasDeAtuacao(updatedUser.getTemasDeAtuacao());
        user.setProfissao(updatedUser.getProfissao());
        user.setGenero(updatedUser.getGenero());

        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        if (!requester.getId().equals(id) && requester.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/usuarios/{id}/temas")
    public ResponseEntity<User> updateTemas(@PathVariable Long id, @RequestBody List<TemaAtuacao> temas) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        if (!requester.getId().equals(id) && requester.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setTemasDeAtuacao(temas);
        return ResponseEntity.ok(userRepository.save(user));
    }
}
