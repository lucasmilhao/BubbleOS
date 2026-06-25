package Modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MODELO (M do MVC).
 *
 * Guarda apenas dados e regras de negócio simples sobre um atalho da área
 * de trabalho. Não importa nada de javax.swing/java.awt — isso é
 * intencional: um Modelo que não depende de Swing pode ser testado com
 * JUnit puro, sem precisar abrir nenhuma janela.
 *
 * Antes, esses dados (i, j, nome, hasComponent, arquivo, pasta...) estavam
 * espalhados dentro da própria classe Atalho (que era ao mesmo tempo
 * JPanel, MouseListener e "dono" da lógica de abrir aplicativos).
 */
public class AtalhoModelo {

    private TipoAtalho tipo;
    private String nome;
    private int linha;
    private int coluna;

    // Dados específicos de cada tipo de atalho. Em um projeto maior isso
    // poderia virar uma pequena hierarquia de "DadosDoAtalho", mas para o
    // tamanho atual do BubbleOS, alguns campos opcionais já resolvem bem
    // e evitam a explosão de subclasses que existia antes.
    private File arquivoAssociado;          // usado quando tipo == BLOCO_DE_NOTAS
    private String urlAssociada;            // usado quando tipo == NAVEGADOR
    private final List<AtalhoModelo> conteudo = new ArrayList<>(); // usado quando tipo == PASTA

    public AtalhoModelo(TipoAtalho tipo, String nome, int linha, int coluna) {
        this.tipo = tipo;
        this.nome = nome;
        this.linha = linha;
        this.coluna = coluna;
    }

    public static AtalhoModelo vazio(int linha, int coluna) {
        return new AtalhoModelo(TipoAtalho.VAZIO, "", linha, coluna);
    }

    public boolean isOcupado() {
        return tipo != TipoAtalho.VAZIO;
    }

    public boolean isPasta() {
        return tipo == TipoAtalho.PASTA;
    }

    /** Move o atalho para uma nova posição na grade (apenas dado, sem repaint). */
    public void moverPara(int novaLinha, int novaColuna) {
        this.linha = novaLinha;
        this.coluna = novaColuna;
    }

    /** Troca o conteúdo deste slot da grade pelo conteúdo de outro atalho. */
    public void trocarConteudoCom(AtalhoModelo outro) {
        TipoAtalho tipoTemp = this.tipo;
        String nomeTemp = this.nome;
        File arquivoTemp = this.arquivoAssociado;
        String urlTemp = this.urlAssociada;

        this.tipo = outro.tipo;
        this.nome = outro.nome;
        this.arquivoAssociado = outro.arquivoAssociado;
        this.urlAssociada = outro.urlAssociada;

        outro.tipo = tipoTemp;
        outro.nome = nomeTemp;
        outro.arquivoAssociado = arquivoTemp;
        outro.urlAssociada = urlTemp;
    }

    public void adicionarAoConteudo(AtalhoModelo item) {
        if (!isPasta()) {
            throw new IllegalStateException("Só é possível adicionar conteúdo a um atalho do tipo PASTA");
        }
        conteudo.add(item);
    }

    // ---- getters / setters --------------------------------------------------

    public TipoAtalho getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtalho tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public File getArquivoAssociado() {
        return arquivoAssociado;
    }

    public void setArquivoAssociado(File arquivoAssociado) {
        this.arquivoAssociado = arquivoAssociado;
    }

    public String getUrlAssociada() {
        return urlAssociada;
    }

    public void setUrlAssociada(String urlAssociada) {
        this.urlAssociada = urlAssociada;
    }

    public List<AtalhoModelo> getConteudo() {
        return conteudo;
    }
}
