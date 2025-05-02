package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Postagem;
import br.com.brasfi.BRASFI.Model.enums.TipoPostagem;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostagemRequestDTO {

    @NotNull
    private String titulo;

    private String paragrafo;
    private String imagemOuVideo;
    private String link;
    private boolean fixado;

    @NotNull
    private TipoPostagem tag; // <-- aqui deve ser enum, não String

    public Postagem toPostagem() {
        return new Postagem(
                0L,
                null, // autor será setado no controller
                tag,
                titulo,
                paragrafo,
                imagemOuVideo,
                link,
                fixado,
                null, // data
                null  // user
        );
    }
}
