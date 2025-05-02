package br.com.brasfi.BRASFI.Model;


import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Postagem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String autor;

    @Enumerated(EnumType.ORDINAL)
    private TipoPostagem tag;

    @Column(name = "titulo")
    private String titulo;

    private String paragrafo;
    private String imagemOuVideo;
    private String link;
    private boolean fixado;
    public static final int LIMITE_PALAVRAS= 300;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comentario> comentarios;











}
