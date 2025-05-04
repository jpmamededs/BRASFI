package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Comentario;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        Long id,
        String titulo,
        String conteudo,
        LocalDateTime dataCriacao,
        String autor,
        Long postagemId,
        Long userId
) {
    public ComentarioResponseDTO(Comentario comentario) {
        this(
                comentario.getId(),
                comentario.getTitulo(),
                comentario.getConteudo(),
                comentario.getDataCriacao(),
                comentario.getAutor(),
                comentario.getPostagem().getId(),
                comentario.getUser().getId()
        );
    }
}
