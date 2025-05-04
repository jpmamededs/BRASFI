package br.com.brasfi.BRASFI.DTO;

import br.com.brasfi.BRASFI.Model.enums.Genero;
import br.com.brasfi.BRASFI.Model.enums.Profissao;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TemaAtuacao;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String userName;
    private String midleName;

    private String biografia;
    private String localizacao;
    private Integer idade;
    private String photo;
    private String linkInstagram;
    private String linkLinkedin;
    private String linkLattes;
    private String linkWhatsapp;

    private List<TemaAtuacao> temasDeAtuacao;
    private List<Profissao> profissao;
    private Genero genero;
    private Role role;
}
