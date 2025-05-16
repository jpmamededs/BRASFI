package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Aula;
import br.com.brasfi.BRASFI.Model.MaterialApoio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialApoioDTO {
    @NotBlank
    private String nomeArquivo;

    @NotBlank
    private String tipo;

    @NotBlank
    private String caminhoArquivo;

    @NotNull
    private Long tamanho;


    public MaterialApoio toEntity(Aula aula) {
        MaterialApoio material = new MaterialApoio();
        material.setNomeArquivo(this.nomeArquivo);
        material.setTipo(this.tipo);
        material.setCaminhoArquivo(this.caminhoArquivo);
        material.setTamanho(this.tamanho);
        material.setAula(aula);
        return material;
    }
}