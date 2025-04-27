package br.com.brasfi.BRASFI.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Título")
    private String titulo;

    @Column(name = "Conteúdo")
    private String conteudo;

    @Column(name = "Data de postagem")
    private LocalDateTime dataCriacao;

    @Column(name = "Autor")
    private String autor;

    @ManyToOne
    private Postagem postagem;

}
