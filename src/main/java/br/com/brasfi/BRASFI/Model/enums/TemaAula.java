package br.com.brasfi.BRASFI.Model.enums;

public enum TemaAula {
    BUROCRACIA,
    CLIMA,
    GESTAO,
    TECNOLOGIA,
    MARKETING,
    OUTRO;
    
    public static boolean isValido(String tema) {
        try {
            TemaAula.valueOf(tema.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}