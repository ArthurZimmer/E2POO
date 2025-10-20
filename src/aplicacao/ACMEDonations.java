package aplicacao;

import dados.CatalogoDoacoes;
import dados.CatalogoDoadores;

public class ACMEDonations {
    private CatalogoDoadores catalogoDoadores;
    private CatalogoDoacoes catalogoDoacoes;

    public ACMEDonations() {
        catalogoDoadores = new CatalogoDoadores();
        catalogoDoacoes = new CatalogoDoacoes(catalogoDoadores);
    }

    public void executar(){
        passo1();
        passo2();
        passo3();
        passo4();
        passo5();
        passo6();
        passo7();
        passo8();
    }

    private void passo1 () {
        catalogoDoadores.lerArquivo();
    }

    private void passo2 () {
        catalogoDoacoes.lerArquivoPereciveis();
    }

    private void passo3 () {
        catalogoDoacoes.lerArquivoDuraveis();
    }

    private void passo4 () {
        catalogoDoadores.buscaEmail();
    }

    private void passo5 () {
        catalogoDoacoes.mostraDoacoes();
    }

    private void passo6 () {
        catalogoDoadores.mostraDoadores();
    }

    private void passo7 () {
        catalogoDoacoes.doacaoPorDoador();
    }
    private void passo8 () { catalogoDoacoes.buscaDuravelPorTipo(); }
}
