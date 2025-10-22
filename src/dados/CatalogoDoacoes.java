package dados;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CatalogoDoacoes {
    private List<Doacao> doacoes = new LinkedList<Doacao>();
    private CatalogoDoadores doadores;
    //private Doador doador;
    private TipoPerecivel tipoPerecivel;
    private TipoDuravel tipoDuravel;

    public CatalogoDoacoes(CatalogoDoadores doadores) {
        this.doadores = doadores;
    }

    public void lerArquivoPereciveis(){
        // escreve o caminho do path, separado por virgula para ser multi S.O
        Path path = Paths.get("recursos","doacoespereciveis.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                // define o charset
                Charset.forName("UTF-8"))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                // separador: ;
                Scanner sc = new Scanner(linha).useDelimiter(";");
                // aqui dentro é o que ele vai fazer a cada palavra do separador
                // le cada elemento de acordo com a ordem da tabela fornecida
                String descricao, valor, quantidade, email, tipo, validade;
                descricao = sc.next();
                valor = sc.next();
                quantidade = sc.next();
                email = sc.next();
                tipo = sc.next();
                validade = sc.next();

                if (!valor.matches("\\d+(\\.\\d+)?")) {
                    throw new IllegalArgumentException("2:ERRO:formato invalido.");
                }

                if (!quantidade.matches("\\d+")) {
                    throw new IllegalArgumentException("2:ERRO:formato invalido.");
                }

                if (!validade.matches("\\d+")) {
                    throw new IllegalArgumentException("2:ERRO:formato invalido.");
                }

                int validadeInt = Integer.parseInt(validade);
                int quantidadeInt = Integer.parseInt(quantidade);
                double valorDouble = Double.parseDouble(valor);
                TipoPerecivel tipoEnum;

                if (TipoPerecivel.tipoExiste(tipo)) {
                    tipoEnum = TipoPerecivel.valueOf(tipo.toUpperCase());
                } else {
                    throw new IllegalArgumentException("2:ERRO: tipo inválido");
                }

                if(!doadores.existeDoadorComEmail(email))
                    throw new IllegalArgumentException ("2:ERRO:doador inexistente.");
                Doador doador = doadores.buscaDoadorPorEmail(email);

                Perecivel doacao;
                if (TipoPerecivel.tipoExiste(tipo)) {
                    doacao = new Perecivel(descricao, valorDouble, quantidadeInt, doador, tipoEnum, validadeInt);
                    doador.setQuantidadeDoacoes(doador.getQuantidadeDoacoes() + 1);
                } else {
                    throw new IllegalArgumentException("2:ERRO: tipo inválido");
                }

                doacoes.add(doacao);
                // DEBUG: System.out.println("doacao perecivel adicionada: " + doacao.toString());
                System.out.println("2: " + descricao + ", " + valor + ", " + quantidade + ", " + tipo + ", " + validade);

            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void lerArquivoDuraveis(){
        // escreve o caminho do path, separado por virgula para ser multi S.O
        Path path = Paths.get("recursos","doacoesduraveis.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                // define o charset
                Charset.forName("UTF-8"))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                // separador: ;
                Scanner sc = new Scanner(linha).useDelimiter(";");
                // aqui dentro é o que ele vai fazer a cada palavra do separador
                String descricao, valor, quantidade, email, tipo;
                descricao = sc.next();
                valor = sc.next();
                quantidade = sc.next();
                email = sc.next();
                tipo = sc.next();

                if (!valor.matches("\\d+(\\.\\d+)?")) {
                    throw new IllegalArgumentException("2:ERRO:formato invalido.");
                }

                if (!quantidade.matches("\\d+")) {
                    throw new IllegalArgumentException("2:ERRO:formato invalido.");
                }

                double valorDouble = Double.parseDouble(valor);
                int quantidadeInt = Integer.parseInt(quantidade);
                TipoDuravel tipoEnum = TipoDuravel.valueOf(tipo.toUpperCase());

                if(!doadores.existeDoadorComEmail(email))
                    throw new IllegalArgumentException ("2:ERRO:doador inexistente.");
                Doador doador = doadores.buscaDoadorPorEmail(email);

                Duravel doacao;
                if (TipoDuravel.tipoExiste(tipo)) {
                    doacao = new Duravel(descricao, valorDouble, quantidadeInt, doador, tipoEnum);
                    doador.setQuantidadeDoacoes(doador.getQuantidadeDoacoes() + 1);
                } else {
                    throw new IllegalArgumentException("2:ERRO: tipo inválido");
                }

                doacoes.add(doacao);
                // DEBUG: System.out.println("doacao duravel adicionada: " + doacao);
                System.out.println("3: " + descricao + ", " + valor + ", " + quantidade + ", " + tipo);

            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void mostraDoacoes() {
        try{
            if (doacoes.isEmpty())
                throw new IllegalArgumentException("5:ERRO:nenhuma doacao cadastrada.");

            for (Doacao d : doacoes) {
                if (d instanceof Perecivel p) {
                    var descricao = p.getDescricao();
                    var valor = p.getValor();
                    var quantidade = p.getQuantidade();
                    var email = p.getDoador().getEmail();
                    var nome = p.getDoador().getNome();
                    var tipo = p.getTipoPerecivel();
                    var validade = p.getValidade();

                    System.out.println("5: " + descricao + ", " + valor + ", " + quantidade + ", " + tipo + ", " + validade + ", " + nome + ", " + email);
                }

                if (d instanceof Duravel D) {
                    var descricao = D.getDescricao();
                    var valor = D.getValor();
                    var quantidade = D.getQuantidade();
                    var nome = D.getDoador().getNome();
                    var email = D.getDoador().getEmail();
                    var tipo = D.getTipoDuravel();

                    System.out.println("5: " + descricao + ", " + valor + ", " + quantidade + ", " + tipo + ", " + nome + ", " + email);
                }
            }
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }

    }

    public void doacaoPorDoador() {
        // escreve o caminho do path, separado por virgula para ser multi S.O
        Path path = Paths.get("recursos","dadosentrada.txt");
        try (BufferedReader br = Files.newBufferedReader(path,
                // define o charset
                Charset.forName("UTF-8"))) {
            String linha = null;
            boolean achouDoador = false;
            while ((linha = br.readLine()) != null) {

                boolean ehDoador = false;
                for (Doador d : doadores.getDoadores()) {
                    if (linha.equals(d.getNome())) {
                        ehDoador = true;
                        achouDoador = true;
                    }
                }

                if (ehDoador == true) {
                    // aqui dentro é o que ele vai fazer a cada palavra do separador
                    String nome = linha;
                    boolean temDoacao = false;

                    for (Doacao d : doacoes) {
                        if (d.getDoador().getNome().equals(nome)) {
                            temDoacao = true;
                            if (d instanceof Perecivel p)
                                System.out.println("7: " + p.getDescricao() + ", " + p.getValor() + ", " + p.getQuantidade() + ", " + p.getTipoPerecivel() + ", " + p.getValidade() + ", " + p.getDoador().getEmail());

                            if (d instanceof Duravel D)
                                System.out.println("7: " + D.getDescricao() + ", " + D.getValor() + ", " + D.getQuantidade() + ", " + D.getTipoDuravel() + ", " + D.getDoador().getEmail());
                        }

                    }
                    if (!temDoacao)
                        throw new IllegalArgumentException("7:ERRO:nenhuma doacao localizada.");
                }
            }
            if (!achouDoador)
                throw new IllegalArgumentException("7: Nenhum doador encontrado");
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void buscaDuravelPorTipo() {
        Path path = Paths.get("recursos","dadosentrada.txt");
        try (BufferedReader br = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String linha = null;
            while ((linha = br.readLine()) != null) {
                try {
                    if (TipoDuravel.tipoExiste(linha)) {
                        boolean achouDoacaoDesteTipo = false;
                        for (Doacao d : doacoes) {
                            if (d instanceof Duravel duravel) {
                                if (duravel.getTipoDuravel().name().equalsIgnoreCase(linha)) {
                                    Doador doador = d.getDoador();
                                    System.out.println("8: " + duravel.getDescricao() + ", " + duravel.getValor() + ", " + duravel.getQuantidade() + ", " + duravel.getTipoDuravel() + ", " + doador.getNome() + ", " + doador.getEmail());
                                    achouDoacaoDesteTipo = true;
                                }
                            }
                        }
                        if (!achouDoacaoDesteTipo) {
                            throw new IllegalArgumentException("8:ERRO:tipo invalido.");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void maiorQuantidadePerecivel () {
        // escreve o caminho do path, separado por virgula para ser multi S.O
        Path path = Paths.get("recursos","dadosentrada.txt");
        try (BufferedReader br = Files.newBufferedReader(path,
                // define o charset
                Charset.forName("UTF-8"))) {
            String linha = null;

            int maiorQuantidade = 0;
            Perecivel doacaoMaiorQuantidade = null;

            while ((linha = br.readLine()) != null) {
                for (Doacao d : doacoes) {
                    if (d instanceof Perecivel p) {
                        if (p.getTipoPerecivel().name().equalsIgnoreCase(linha)) {
                            if (p.getQuantidade() > maiorQuantidade) {
                                doacaoMaiorQuantidade = p;
                                maiorQuantidade = d.getQuantidade();
                            }
                        }
                    }
                }
            }
            if (maiorQuantidade == 0) {
                throw new IllegalArgumentException("9: ERRO: nenhuma doacao localizada.");
            } else {
                System.out.println("9: " + doacaoMaiorQuantidade.getDescricao() + ", " + doacaoMaiorQuantidade.getValor() + ", " + doacaoMaiorQuantidade.getQuantidade() + ", " + doacaoMaiorQuantidade.getTipoPerecivel() + ", " + doacaoMaiorQuantidade.getValidade() + ", " + doacaoMaiorQuantidade.getDoador().getNome() + ", " + doacaoMaiorQuantidade.getDoador().getEmail());
            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void maiorDoador() {
        try{
            if (doadores.getDoadores().isEmpty())
                throw new IllegalArgumentException("10:ERRO:nenhum doador localizado.");

            double maiorMontante = 0;
            Doador doadorVencedor = null;

            for (Doador doadorAtual : doadores.getDoadores()) {
                double totalDesteDoador = 0;
                for (Doacao d : doacoes) {
                    if (d.getDoador().equals(doadorAtual)) {
                        totalDesteDoador += d.getValor();
                    }
                }
                if (totalDesteDoador >= maiorMontante) {
                    maiorMontante = totalDesteDoador;
                    doadorVencedor = doadorAtual;
                }
            }
            String montanteFormatado = String.format(Locale.US, "%.2f", maiorMontante);
            System.out.println("10: " + doadorVencedor.getNome() + ", " + doadorVencedor.getEmail() + ", " + montanteFormatado);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }
}
