package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostagemResponseDTO {

    private Long id;
    private String titulo;
    private String paragrafo;
    private TipoPostagem tag;
    private String imagemOuVideo;
    private String videoLink;
    private String link;
    private boolean fixado;
    private String autor;
    private List<ComentarioResponseDTO> comentarios;
    private LocalDateTime dataCriacao;

    public PostagemResponseDTO(Postagem postagem) {
        this.id = postagem.getId();
        this.titulo = postagem.getTitulo();
        this.paragrafo = postagem.getParagrafo();
        this.tag = postagem.getTag();
        this.imagemOuVideo = postagem.getImagemOuVideo();
        this.videoLink = postagem.getVideoLink();
        this.link = postagem.getLink();
        this.fixado = postagem.isFixado();
        this.autor = postagem.getUser().getUsername();
        this.dataCriacao = postagem.getDataCriacao();

        this.comentarios = postagem.getComentarios()
                .stream()
                .map(ComentarioResponseDTO::new)
                .collect(Collectors.toList());
    }
}
