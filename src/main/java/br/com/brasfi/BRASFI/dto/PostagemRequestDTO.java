package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostagemRequestDTO(
        @NotBlank String autor,
        @NotNull TipoPostagem tag,
        @NotBlank String titulo,
        @NotBlank String paragrafo,
        String imagemOuVideo,
        String link,
        boolean fixado
) {


    public PostagemRequestDTO(Postagem postagem) {
        this(
                postagem.getAutor(),
                postagem.getTag(),
                postagem.getTitulo(),
                postagem.getParagrafo(),
                postagem.getImagemOuVideo(),
                postagem.getLink(),
                postagem.isFixado()
        );
    }


    public Postagem toPostagem() {
        return new Postagem(
                0L,
                autor(),
                tag(),
                titulo(),
                paragrafo(),
                imagemOuVideo(),
                link(),
                fixado,
                null,
                null
        );
    }
}