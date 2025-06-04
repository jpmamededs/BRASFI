package br.com.brasfi.BRASFI.Model;

import br.com.brasfi.BRASFI.Model.enums.Genero;
import br.com.brasfi.BRASFI.Model.enums.Profissao;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TemaAtuacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
@AllArgsConstructor
public class User implements UserDetails {

    public static final int LIMITE_BIO = 300;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    private String username;


    private String midleName;

    private String biografia;
    private String localizacao;
    private int idade;
    private String photo;
    private String linkInstagram;
    private String linkLinkedin;
    private String linkLattes;
    private String linkWhatsapp;

    @ElementCollection
    private List<TemaAtuacao> temasDeAtuacao = new ArrayList<>();

    @ElementCollection
    private List<Profissao> profissao = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Postagem> postagens = new ArrayList<>();

    public User(){
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role); // pois seu role já é um GrantedAuthority
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username; // ou username, dependendo do login
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
