package br.com.brasfi.BRASFI.dto;



import br.com.brasfi.BRASFI.Model.User;
import br.com.brasfi.BRASFI.Model.enums.Genero;
import br.com.brasfi.BRASFI.Model.enums.Profissao;
import br.com.brasfi.BRASFI.Model.enums.Role;
import br.com.brasfi.BRASFI.Model.enums.TemaAtuacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public record UserDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String username,
        String midleName,
        String biografia,
        String localizacao,
        Integer idade,
        String photo,
        String linkInstagram,
        String linkLinkedin,
        String linkLattes,
        String linkWhatsapp,
        List<TemaAtuacao> temasDeAtuacao,
        List<Profissao> profissao,
        Genero genero,
        @NotNull Role role
) {
    public User toUser() {
        return new User(
                null,
                email,
                password,
                username,
                midleName,
                biografia,
                localizacao,
                idade != null ? idade : 0,
                photo,
                linkInstagram,
                linkLinkedin,
                linkLattes,
                linkWhatsapp,
                temasDeAtuacao != null ? temasDeAtuacao : new ArrayList<>(),
                profissao != null ? profissao : new ArrayList<>(),
                genero,
                role != null ? role : Role.USER,
                new ArrayList<>()
        );
    }
}

