package Visao;

import Jogos.Dinossauro.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;

public final class Dinossauro extends Atalho {

    public Dinossauro(int i, int j) {
        super(i, j);

        this.setImagem(new ImageIcon(getClass().getResource("/Jogos/Dinossauro/img/dino.png")).getImage());
        this.setOpaque(false);
        this.setHasComponent(true);
        this.setNome("Jogo Dinossauro");
        if(getParent() instanceof TelaPrincipal tela) {
            System.out.println("Pipippoop");
            tela.adicionarAtalho(this);
        }
    }

    
    @Override
    public void abrirMenu(MouseEvent e) {
        super.abrirMenu(e);

        JMenuItem abrir = new JMenuItem("Abrir");
        abrir.addActionListener(e1 -> abrir());
        this.getMenu().add(abrir);
        this.getMenu().show(this, e.getX(), e.getY());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void abrir() {
        this.setRun(true);
        new Game();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) abrir();
        repaint();
    }

}
