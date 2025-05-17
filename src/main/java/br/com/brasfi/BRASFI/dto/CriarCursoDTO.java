package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Curso;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.AreaDoConhecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CriarCursoDTO {
    @NotNull
    private AreaDoConhecimento areaConhecimento;

    private String areaCustomizada;

    @NotBlank
    private String titulo;

    @NotBlank
    private String duracao;

    private List<ModuloDTO> modulos;


    public Curso toEntity() {
        Curso curso = new Curso();
        curso.setAreaConhecimento(this.areaConhecimento);
        curso.setAreaCustomizada(this.areaCustomizada);
        curso.setTitulo(this.titulo);
        curso.setDuracao(this.duracao);

        return curso;
    }
}