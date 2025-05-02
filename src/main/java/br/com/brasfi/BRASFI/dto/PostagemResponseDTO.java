package br.com.brasfi.BRASFI.dto;



import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import lombok.Getter;

@Getter
public class PostagemResponseDTO {

    private Long id;
    private String titulo;
    private String paragrafo;
    private TipoPostagem tag;
    private String imagemOuVideo;
    private String link;
    private boolean fixado;
    private String autor;

    public PostagemResponseDTO(Postagem postagem) {
        this.id = postagem.getId();
        this.titulo = postagem.getTitulo();
        this.paragrafo = postagem.getParagrafo();
        this.tag = postagem.getTag();
        this.imagemOuVideo = postagem.getImagemOuVideo();
        this.link = postagem.getLink();
        this.fixado = postagem.isFixado();
        this.autor = postagem.getUser().getUsername(); // ou email, login...
    }


}