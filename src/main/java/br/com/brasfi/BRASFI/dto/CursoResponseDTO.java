package br.com.brasfi.BRASFI.dto;


import br.com.brasfi.BRASFI.Model.Curso;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CursoResponseDTO {
    private Long id;
    private String titulo;
    private String duracao;
    private String areaConhecimento;
    private String autor;
    private List<ModuloResponseDTO> modulos;

    public static CursoResponseDTO fromEntity(Curso curso) {
        CursoResponseDTO dto = new CursoResponseDTO();
        dto.setId(curso.getId());
        dto.setTitulo(curso.getTitulo());
        dto.setDuracao(curso.getDuracao());
        dto.setAreaConhecimento(curso.getAreaConhecimento().toString().toLowerCase());
        dto.setAutor(curso.getAutor().getUsername());


        if (curso.getModulos() != null) {
            dto.setModulos(curso.getModulos().stream()
                    .map(ModuloResponseDTO::fromEntity)
                    .collect(Collectors.toList()));
        } else {
            dto.setModulos(Collections.emptyList());
        }

        return dto;
    }


}