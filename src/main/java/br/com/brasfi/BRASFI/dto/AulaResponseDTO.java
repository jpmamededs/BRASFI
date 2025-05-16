package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Aula;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class AulaResponseDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String nivel;
    private String tema;
    private String autor;
    private String tipoConteudo;
    private String conteudo;
    private Integer ordem;
    private List<MaterialResponseDTO> materiais;

    public static AulaResponseDTO fromEntity(Aula aula) {
        AulaResponseDTO dto = new AulaResponseDTO();
        dto.setId(aula.getId());
        dto.setTitulo(aula.getTitulo());
        dto.setDescricao(aula.getDescricao());
        dto.setNivel(aula.getNivel().toString());
        dto.setTema(aula.getTema().toString());
        dto.setAutor(aula.getAutor());
        dto.setTipoConteudo(aula.isVideo() ? "VIDEO" : "TEXTO");
        dto.setConteudo(aula.isVideo() ? aula.getUrlVideo() : aula.getConteudoTexto());
        dto.setOrdem(aula.getOrdem());


        List<MaterialResponseDTO> materiaisDTO = aula.getMateriais().stream()
                .map(MaterialResponseDTO::fromEntity)
                .collect(Collectors.toList());

        dto.setMateriais(materiaisDTO);
        return dto;
    }


}