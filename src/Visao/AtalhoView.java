package Visao;

import Modelo.AtalhoModelo;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;
import javax.swing.JPanel;

/**
 * VISÃO (V do MVC).
 *
 * Responsabilidade única: desenhar o estado atual do AtalhoModelo na tela.
 * Não sabe abrir aplicativos, não sabe o que fazer quando é clicado, não
 * guarda estado de "atalho ativo" globalmente — apenas reflete o que o
 * Controlador mandar (setAtivo, setMouseDentro, atualizarPosicao...).
 *
 * Antigamente esta mesma classe (Atalho) também implementava
 * MouseListener e continha métodos como salvarImagem(), irTelaPrincipal()
 * e abrirMenu() com lógica de negócio. Tudo isso migrou para
 * Controle.AtalhoControlador — a View deixou de saber qualquer coisa
 * sobre "o que" cada ícone representa.
 */
public class AtalhoView extends JPanel {

    public static final int LARGURA = 80;
    public static final int ALTURA = 85;
    private static final int LARGURA_IMAGEM = 33;
    private static final int ALTURA_IMAGEM = 53;

    private final AtalhoModelo modelo;
    private Image icone;
    private boolean ativo;
    private boolean mouseDentro;

    public AtalhoView(AtalhoModelo modelo, Image icone) {
        this.modelo = modelo;
        this.icone = icone;
        this.setOpaque(false);
        atualizarPosicao();
    }

    /** Recalcula a posição na grade a partir do modelo (chamado pelo controlador). */
    public void atualizarPosicao() {
        this.setBounds(modelo.getColuna() * LARGURA, modelo.getLinha() * ALTURA, LARGURA, ALTURA);
    }

    public void setIcone(Image icone) {
        this.icone = icone;
        repaint();
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
        repaint();
    }

    public void setMouseDentro(boolean mouseDentro) {
        this.mouseDentro = mouseDentro;
        repaint();
    }

    public boolean isAtivo() {
        return ativo;
    }

    public AtalhoModelo getModelo() {
        return modelo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        desenharSelecao(g2d);
        desenharHover(g2d);
        desenharIcone(g2d);
        desenharNome(g2d);
    }

    private void desenharSelecao(Graphics2D g2d) {
        if (ativo) {
            g2d.setColor(new Color(0, 0, 128, 64));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.WHITE);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    private void desenharHover(Graphics2D g2d) {
        if (mouseDentro && modelo.isOcupado()) {
            g2d.setColor(new Color(255, 255, 255, 64));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.WHITE);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    private void desenharIcone(Graphics2D g2d) {
        if (icone != null) {
            g2d.drawImage(icone, (getWidth() / 2) - (LARGURA_IMAGEM / 2), 6, LARGURA_IMAGEM, ALTURA_IMAGEM, null);
        }
    }

    private void desenharNome(Graphics2D g2d) {
        String nome = modelo.getNome();
        if (nome == null || nome.isBlank()) return;

        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(nome)) / 2;

        g2d.setColor(Color.BLACK);
        g2d.drawString(nome, x, getHeight() - 4);
        g2d.setColor(Color.WHITE);
        g2d.drawString(nome, x, getHeight() - 5);
    }
}
