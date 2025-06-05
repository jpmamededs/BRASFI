package br.com.brasfi.BRASFI.Model;

import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Postagem {

    public static final int LIMITE_PALAVRAS= 300;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String autor;

    @Enumerated(EnumType.STRING)
    private TipoPostagem tag;

    @Column(name = "titulo")
    private String titulo;

    private String paragrafo;
    private String imagemOuVideo;
    private String videoLink;
    private String link;
    private boolean fixado;
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Comentario> comentarios;

    public Postagem(){
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}
