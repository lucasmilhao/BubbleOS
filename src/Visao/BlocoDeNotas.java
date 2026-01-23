package Visao;

import Aplicativos.Notas;

import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public final class BlocoDeNotas extends Atalho {
    
    private File arquivo = null;

    public BlocoDeNotas(){}

    public BlocoDeNotas(int i, int j, File arquivo) {
        super(i, j);
        
        this.arquivo = arquivo;
        
        this.setImagem(new ImageIcon(getClass().getResource("/Imagens/notas.png")).getImage());
        this.setOpaque(false);
        this.setHasComponent(true);
        this.setNome("Bloco de notas");
    }

    public BlocoDeNotas(int i, int j, File arquivo, Pasta pasta) {
        super(i, j);
        
        this.arquivo = arquivo;
        
        this.setImagem(new ImageIcon(getClass().getResource("/Imagens/notas.png")).getImage());
        this.setOpaque(false);
        this.setHasComponent(true);
        this.setNome("Bloco de notas");

    }    
    
    @Override
    public void abrirMenu(MouseEvent e) {
        super.abrirMenu(e);

        JMenuItem abrir = new JMenuItem("Abrir");
        abrir.addActionListener(e1 -> abrir());
        this.getMenu().add(abrir);
        this.getMenu().show(this, e.getX(), e.getY());
    }


    public void abrir() {
        this.setRun(true);
        new Notas(arquivo);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) abrir();
        repaint();
    }
}
