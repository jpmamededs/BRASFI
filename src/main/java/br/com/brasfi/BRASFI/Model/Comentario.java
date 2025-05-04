package br.com.brasfi.BRASFI.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String conteudo;

    private LocalDateTime dataCriacao;

    private String autor;

    @ManyToOne
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comentario() {}

    public Comentario(String titulo, String conteudo, LocalDateTime dataCriacao, String autor, Postagem postagem, User user) {
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.autor = autor;
        this.postagem = postagem;
        this.user = user;
    }
}
