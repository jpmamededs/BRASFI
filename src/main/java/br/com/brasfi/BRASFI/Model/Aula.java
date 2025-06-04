package br.com.brasfi.BRASFI.Model;

import br.com.brasfi.BRASFI.Model.enums.NivelDificuldade;
import br.com.brasfi.BRASFI.Model.enums.TemaAula;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelDificuldade nivel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TemaAula tema;

    @Column(nullable = true)
    private String temaCustomizado;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String conteudoTexto;

    @Column(nullable = true)
    private String urlVideo;

    @Column(nullable = false)
    private boolean isVideo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    @JsonBackReference
    private Modulo modulo;

    @Column(nullable = false)
    private Integer ordem;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true,fetch =FetchType.LAZY)
    private List<MaterialApoio> materiais = new ArrayList<>();

    public Aula(){};

    public String getTemaCompleto() {
        return this.tema == TemaAula.OUTRO ?
                this.temaCustomizado :
                this.tema.toString().toLowerCase();
    }
}