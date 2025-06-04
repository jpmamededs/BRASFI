package br.com.brasfi.BRASFI.Service;

import java.util.Collections;
import java.util.Optional;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).map(user -> {

            String role = "ROLE_" + (user.getRole() != null ? user.getRole().name() : "USER");

            GrantedAuthority authority = new SimpleGrantedAuthority(role);

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(Collections.singletonList(authority))
                    .build();
        })
        .orElseThrow(() -> {
            return new UsernameNotFoundException("Usuário não encontrado: " + username);
        });
    }
}
