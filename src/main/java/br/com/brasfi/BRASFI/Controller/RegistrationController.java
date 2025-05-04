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
    public User createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
        }

    }