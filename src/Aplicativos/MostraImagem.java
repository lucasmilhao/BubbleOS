package Aplicativos;

import javax.swing.JFrame;

import Visao.Imagem;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class MostraImagem extends JPanel {
    
    private Image imagem;
    private JFrame frame;

    public MostraImagem(Imagem imagem) {
        this.imagem = imagem.getImagem();
        this.setPreferredSize(new Dimension(600, 340));
        this.frame = new JFrame();
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(1);
        frame.setTitle(imagem.getNome());
        frame.setSize(600, 340);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        if(imagem != null) g.drawImage(imagem, 0, 0, getWidth(), getHeight(), null);
    }

}
