package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Aula;
import br.com.brasfi.BRASFI.Model.Modulo;
import br.com.brasfi.BRASFI.Model.enums.NivelDificuldade;
import br.com.brasfi.BRASFI.Model.enums.TemaAula;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AulaDTO {
    @NotBlank
    private String titulo;

    private String descricao;

    @NotNull
    private NivelDificuldade nivel;

    @NotNull
    private TemaAula tema;

    private String temaCustomizado;

    @NotBlank
    private String autor;

    private String conteudoTexto;

    private String urlVideo;

    @NotNull
    private Boolean isVideo;

    @NotNull
    private Integer ordem;

    @Valid
    private List<MaterialApoioDTO> materiais;


    public Aula toEntity(Modulo modulo) {
        Aula aula = new Aula();
        aula.setTitulo(this.titulo);
        aula.setDescricao(this.descricao);
        aula.setNivel(this.nivel);
        aula.setTema(this.tema);
        aula.setTemaCustomizado(this.temaCustomizado);
        aula.setAutor(this.autor);
        aula.setConteudoTexto(this.conteudoTexto);
        aula.setUrlVideo(this.urlVideo);
        aula.setVideo(this.isVideo);
        aula.setOrdem(this.ordem);
        aula.setModulo(modulo);
        return aula;
    }
}