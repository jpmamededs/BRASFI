package br.com.brasfi.BRASFI.Model;

import br.com.brasfi.BRASFI.Model.enums.Genero;
import br.com.brasfi.BRASFI.Model.enums.Profissao;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TemaAtuacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class User {

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
    private String userName;

    @Column(nullable = false)
    @NotBlank(message = "Sobrenome é obrigatório")
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

    @OneToMany
    private Postagem postagens;
}
