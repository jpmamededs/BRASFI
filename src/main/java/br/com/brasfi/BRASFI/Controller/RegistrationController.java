package br.com.brasfi.BRASFI.Controller;

import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping(value = "/req/signup", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {

        User user = userDTO.toUser();


        user.setPassword(passwordEncoder.encode(user.getPassword()));


        User savedUser = userRepository.save(user);


        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
