package br.com.brasfi.BRASFI.dto;


import br.com.brasfi.BRASFI.Model.Curso;
import br.com.brasfi.BRASFI.Model.Modulo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModuloDTO {
    @NotBlank
    private String nome;

    @NotNull
    private Integer ordem;

    @Valid
    private List<AulaDTO> aulas;


    public Modulo toEntity(Curso curso) {
        Modulo modulo = new Modulo();
        modulo.setNome(this.nome);
        modulo.setOrdem(this.ordem);
        modulo.setCurso(curso);
        return modulo;
    }
}