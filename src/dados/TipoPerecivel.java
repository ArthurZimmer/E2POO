package dados;

public enum TipoPerecivel {
    ALIMENTO("ALIMENTO"),
    MEDICAMENTO("MEDICAMENTO");
    private String nome;

    TipoPerecivel(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static boolean tipoExiste (String tipo) {
        for (TipoPerecivel t : values()) {
            if (t.name().equalsIgnoreCase(tipo))
                return true;
        }
        return false;
    }
}
