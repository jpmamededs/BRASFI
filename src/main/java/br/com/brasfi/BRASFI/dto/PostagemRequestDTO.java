package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PostagemRequestDTO(

        TipoPostagem tag,
        @NotBlank String titulo,
        @NotBlank String paragrafo,
        String imagemOuVideo,
        String link,
        boolean fixado
) {


    public PostagemRequestDTO(Postagem postagem) {
        this(

                postagem.getTag(),
                postagem.getTitulo(),
                postagem.getParagrafo(),
                postagem.getImagemOuVideo(),
                postagem.getLink(),
                postagem.isFixado()
        );
    }


    public Postagem toPostagem(User user) {
        return new Postagem(
                0L,
                user.getUsername(),

                tag(),
                titulo(),
                paragrafo(),
                imagemOuVideo(),
                link(),
                fixado,
                null,
                null,
                LocalDateTime.now()
        );
    }
}