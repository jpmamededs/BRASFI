package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Comentario;
import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ComentarioRequestDTO(
        @NotBlank String titulo,
        @NotBlank String conteudo,
        @NotNull LocalDateTime dataCriacao,
        @NotNull Long postagemId
) {

    public Comentario toComentario(Postagem postagem, User user) {
        return new Comentario(
                titulo,
                conteudo,
                dataCriacao,
                user.getUsername(),
                postagem,
                user
        );
    }
}
