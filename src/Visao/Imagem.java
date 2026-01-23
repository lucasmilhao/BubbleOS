package Visao;

import Aplicativos.MostraImagem;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class Imagem extends Atalho {

    private Pasta pasta;

    public Imagem(int i, int j, Pasta pasta) {
        super(i, j);

        this.pasta = pasta;
        
        this.setOpaque(false);
        this.setHasComponent(true);
    }
    
    @Override
    public void abrirMenu(MouseEvent e) {
        super.abrirMenu(e);

        JMenuItem mudarFundo = new JMenuItem("Selecionar como plano de fundo");
        mudarFundo.addActionListener(e1 -> setarPlano());
        JMenuItem abrir = new JMenuItem("Abrir");
        abrir.addActionListener(e1 -> abrir());
        this.getMenu().setPreferredSize(new Dimension(200, 75));
        this.getMenu().add(abrir);
        this.getMenu().add(mudarFundo);

        this.getMenu().show(this, e.getX(), e.getY());
    }

    public void setarPlano() {
        if(pasta.getParent() instanceof TelaPrincipal tela) {
            tela.setPlanoFundo(this.getImagem());
            tela.setCorSolida(false);
            tela.repaint();
        }
    }
    
    public void abrir() {
        this.setRun(true);
        new MostraImagem(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) abrir();
        repaint();
    }
}
