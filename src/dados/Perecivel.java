package dados;

public class Perecivel extends Doacao{
    private int validade;
    private TipoPerecivel tipoPerecivel;

    public Perecivel(String descricao, double valor, int quantidade, Doador doador, TipoPerecivel tipo, int validade) {
        super(descricao, valor, quantidade, doador);
        this.tipoPerecivel = tipo;
        this.validade = validade;
    }

    public String geraResumo(){
        return "";
    }

    public int getValidade() {
        return validade;
    }

    public TipoPerecivel getTipoPerecivel() {
        return tipoPerecivel;
    }
}
