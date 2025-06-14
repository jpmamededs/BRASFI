package br.com.brasfi.BRASFI.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonBackReference
    private Curso curso;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true,fetch= FetchType.LAZY)
    @OrderBy("ordem ASC")
    @JsonManagedReference
    private List<Aula> aulas = new ArrayList<>();

    public Modulo(){};
}