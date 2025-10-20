package dados;

public enum TipoDuravel {
    ROUPA("ROUPA"),
    BRINQUEDO("BRINQUEDO"),
    ELETRODOMESTICO("ELETRODOMESTICO"),
    MOVEL("MOVEL");
    private String nome;

    TipoDuravel(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static boolean tipoExiste (String tipo) {
        for (TipoDuravel t : values()) {
            if (t.name().equalsIgnoreCase(tipo))
                return true;
        }
        return false;
    }
}
