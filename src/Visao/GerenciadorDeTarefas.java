package Visao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GerenciadorDeTarefas extends JPanel {

    private Dimension d;
    private Image imagem;
    private int ALTURA;
    private int LARGURA;

    public GerenciadorDeTarefas(Dimension d) {
        this.ALTURA = 65;
        this.imagem = new ImageIcon(getClass().getResource("/Imagens/tarefas.png")).getImage();
        this.LARGURA = (int) d.getWidth();
        this.d = new Dimension(LARGURA, ALTURA);
        this.setPreferredSize(this.d);
        this.setLayout(null);
        this.add(new Relogio());
    }

    @Override
    public void paintComponent(Graphics g) {
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(19, 89, 158));
        g.drawImage(imagem, 0, (int) d.getHeight() - 65, (int) d.getWidth(), 65, null);
    }

}
