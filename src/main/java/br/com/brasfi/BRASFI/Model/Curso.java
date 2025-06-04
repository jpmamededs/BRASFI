package br.com.brasfi.BRASFI.Model;

import br.com.brasfi.BRASFI.Model.enums.AreaDoConhecimento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AreaDoConhecimento areaConhecimento;

    private String areaCustomizada;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String duracao;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @OrderBy("ordem ASC")
    @JsonManagedReference
    private List<Modulo> modulos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User autor;

    public String getAreaCompleta() {
        return this.areaConhecimento == AreaDoConhecimento.OUTRA ?
                this.areaCustomizada :
                this.areaConhecimento.toString().toLowerCase();
    }

    public Curso(){}
}
