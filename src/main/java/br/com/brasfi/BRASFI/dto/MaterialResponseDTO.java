package br.com.brasfi.BRASFI.dto;

import br.com.brasfi.BRASFI.Model.MaterialApoio;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MaterialResponseDTO {
    private Long id;
    private String nomeArquivo;
    private String tipo;
    private String downloadUrl;

    public static MaterialResponseDTO fromEntity(MaterialApoio material) {
        MaterialResponseDTO dto = new MaterialResponseDTO();
        dto.setId(material.getId());
        dto.setNomeArquivo(material.getNomeArquivo());
        dto.setTipo(material.getTipo());
        dto.setDownloadUrl("/api/materiais/download/" + material.getId());
        return dto;
    }
}