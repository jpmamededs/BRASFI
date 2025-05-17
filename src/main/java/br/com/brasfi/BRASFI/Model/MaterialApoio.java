package br.com.brasfi.BRASFI.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class MaterialApoio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nomeArquivo;
    
    @Column(nullable = false)
    private String tipo;
    
    @Column(nullable = false)
    private String caminhoArquivo;
    
    @Column(nullable = false)
    private Long tamanho;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id", nullable = false)
    @JsonIgnore
    private Aula aula;

    public MaterialApoio(){};
    

}