package dados;

public class Doador {
    private String nome;
    private String email;
    private int quantidadeDoacoes;

    public Doador(String nome, String email, int quantidadeDoacoes) {
        this.nome = nome;
        this.email = email;
        this.quantidadeDoacoes = quantidadeDoacoes;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getQuantidadeDoacoes() {
        return quantidadeDoacoes;
    }

    public void setQuantidadeDoacoes(int quantidadeDoacoes) {
        this.quantidadeDoacoes = quantidadeDoacoes;
    }

    @Override
    public String toString() {
        return "Doador{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
