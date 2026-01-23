package Visao;

import Sons.Som;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Explorar extends JFrame {
    
    private Pasta pasta;
    private JPanel painel;
    private JLabel label;
    private JScrollPane scroll;

    public Explorar(Pasta pasta) {
        this.pasta = pasta;
        this.setTitle(pasta.getNome());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(640, 300);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(1);
        this.setLayout(new BorderLayout());
        
        painel = new JPanel();
        painel.setLayout(new FlowLayout());
        
        scroll = new JScrollPane(painel);
        scroll.setBounds(0, 0, 630, 260);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        label = new JLabel("Ainda não há arquivos nesta pasta.");
        label.setSize(198, 30);
        label.setBounds((getWidth() / 2) - (198 / 2), 15, 198, 30);
        painel.add(label);
        
        this.add(scroll, BorderLayout.CENTER);
        Som.padrao(Som.getClick());
        exibirAtalhos();
        this.setVisible(true);
    }


    public void exibirAtalhos() {
        if(!pasta.getAtalhos().isEmpty()) {
            painel.remove(label);
            painel.setLayout(null);

            for(int i = 0; i < pasta.getContadorI() + 1; i++) {
                for(int j = 0; j < pasta.getContadorJ(); j++) {
                    Atalho a = pasta.getAtalhos().get(i + j);
                    a.setRun(false);
                    a.setActive(false);
                    a.removeMouseMotionListener(a.getMouseAdapter());
                    a.setI(i);
                    a.setJ(j);
                    a.recalcularBounds(a.getI(), a.getJ());
                    painel.add(a);
                }
            }
        }
        else {
            painel.add(label);
            painel.setLayout(new FlowLayout());
            repaint();
        }
    }
}
