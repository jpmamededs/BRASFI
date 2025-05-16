package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.Modulo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ModuloResponseDTO {
    private Long id;
    private String nome;
    private Integer ordem;
    private List<AulaResponseDTO> aulas;

    public static ModuloResponseDTO fromEntity(Modulo modulo) {
        ModuloResponseDTO dto = new ModuloResponseDTO();
        dto.setId(modulo.getId());
        dto.setNome(modulo.getNome());
        dto.setOrdem(modulo.getOrdem());


        List<AulaResponseDTO> aulasDTO = modulo.getAulas().stream()
                .map(AulaResponseDTO::fromEntity)
                .collect(Collectors.toList());

        dto.setAulas(aulasDTO);
        return dto;
    }

    // Getters e Setters
}