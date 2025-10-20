package dados;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class CatalogoDoadores {
    // ver a respeito de ser array ou linked list
    private List<Doador> doadores = new LinkedList<Doador>();
    public CatalogoDoadores(){
        doadores = new LinkedList<Doador>();
    }

    public List<Doador> getDoadores() {
        return doadores;
    }

    public void lerArquivo(){
        // escreve o caminho do path, separado por virgula para ser multi S.O
        Path path = Paths.get("recursos","doadores.csv");
        try (BufferedReader br = Files.newBufferedReader(path,
                // define o charset
                Charset.forName("UTF-8"))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                // separador: ;
                Scanner sc = new Scanner(linha).useDelimiter(";");
                // aqui dentro é o que ele vai fazer a cada palavra do separador
                String nome, email;
                nome = sc.next();
                email = sc.next();
                int quantidadeDoacoes = 0;
                boolean doadorRepetido = false;
                int quantidadeRepetido = 1;

                for (int i = 0; i < doadores.size(); i++) {
                    Doador d = doadores.get(i);
                    if (d.getNome().equals(nome) && d.getEmail().equals(email)){
                        doadorRepetido = true;
                        email = nome + quantidadeRepetido + "@email.com";
                        quantidadeRepetido++;
                        throw new IllegalArgumentException ("1: ERRO: Usuário repetido.");

                    }
                }

                if (!doadorRepetido) {
                    Doador doador = new Doador(nome, email, quantidadeDoacoes);
                    doadores.add(doador);
                    System.out.println("1" + ": " + nome +", "+email);
                }
            }
        }
        catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public boolean existeDoadorComEmail (String email) {
        for (Doador d : doadores) {
            if (d.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public Doador buscaDoadorPorEmail (String email) {
        for (Doador d : doadores) {
            if (d.getEmail().equalsIgnoreCase(email)) {
                return d;
            }
        }
        return null;
    }

    public void buscaEmail() {
        // escreve o caminho do path, separado por virgula para ser multi S.O
        Path path = Paths.get("recursos","dadosentrada.txt");
        try (BufferedReader br = Files.newBufferedReader(path,
                // define o charset
                Charset.forName("UTF-8"))) {
            String linha = null;
            while ((linha = br.readLine()) != null) {

                boolean ehEmail = false;
                for (int i = 0; i < linha.length(); i++) {
                    if (linha.charAt(i) == '@')
                        ehEmail = true;
                }

                if (ehEmail == true) {
                    Scanner sc = new Scanner(linha).useDelimiter(" ");
                    // aqui dentro é o que ele vai fazer a cada palavra do separador
                    String email;
                    email = sc.next();

                    Doador doador = buscaDoadorPorEmail(email);
                    if (doador == null)
                        throw new IllegalArgumentException ("4:ERRO:e-mail inexistente.");

                    System.out.println("4: " + doador.getNome() +", "+email);
                }
            }
        }
        catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void mostraDoadores() {
        try{
            if (doadores.isEmpty())
                throw new IllegalArgumentException ("6:ERRO:nenhum doador encontrado.");

            for (Doador d : doadores) {
                System.out.println("6: " + d.getNome() + ", " + d.getEmail() + ", " + d.getQuantidadeDoacoes());
            }
        } catch (IllegalArgumentException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }
}
