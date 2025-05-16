package br.com.brasfi.BRASFI.Model.enums;

public enum AreaDoConhecimento {

    EMPREENDEDORISMO,
    SUSTENTABILIDADE,
    FINANCAS,
    GOVERNANCA,
    OUTRA;

    public static boolean contains(String value) {
        try {
           AreaDoConhecimento.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
