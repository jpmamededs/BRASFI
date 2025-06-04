package br.com.brasfi.BRASFI.Service;

import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TemaAtuacao;
import br.com.brasfi.BRASFI.Repository.UserRepository;
import br.com.brasfi.BRASFI.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(user -> {
            String role = "ROLE_" + (user.getRole() != null ? user.getRole().name() : "USER");
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(Collections.singletonList(authority))
                    .build();
        }).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.username()).isPresent()) {
            throw new IllegalArgumentException("Username já existe.");
        }

        User user = userDTO.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUserProfile(Long id, User updatedUserDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        if (!requester.getId().equals(id) && requester.getRole() != Role.ADMIN) {
            throw new SecurityException("Usuário não tem permissão para editar este perfil.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setUsername(updatedUserDTO.getUsername());
        user.setEmail(updatedUserDTO.getEmail());

        if (updatedUserDTO.getPassword() != null && !updatedUserDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));
        }

        user.setBiografia(updatedUserDTO.getBiografia());
        user.setLocalizacao(updatedUserDTO.getLocalizacao());
        user.setIdade(updatedUserDTO.getIdade());
        user.setPhoto(updatedUserDTO.getPhoto());
        user.setLinkInstagram(updatedUserDTO.getLinkInstagram());
        user.setLinkLinkedin(updatedUserDTO.getLinkLinkedin());
        user.setLinkLattes(updatedUserDTO.getLinkLattes());
        user.setLinkWhatsapp(updatedUserDTO.getLinkWhatsapp());
        user.setTemasDeAtuacao(updatedUserDTO.getTemasDeAtuacao());
        user.setProfissao(updatedUserDTO.getProfissao());
        user.setGenero(updatedUserDTO.getGenero());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));


        if (!requester.getId().equals(id) && requester.getRole() != Role.ADMIN) {
            throw new SecurityException("Usuário não tem permissão para deletar este perfil.");
        }

        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado para deleção.");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public User updateTemas(Long id, List<TemaAtuacao> temas) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        if (!requester.getId().equals(id) && requester.getRole() != Role.ADMIN) {
            throw new SecurityException("Usuário não tem permissão para atualizar os temas deste perfil.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setTemasDeAtuacao(temas);
        return userRepository.save(user);
    }
}