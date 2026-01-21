package Visao;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Renomear extends JFrame {
    
    private JTextField nome;
    private JButton cancelar;
    private JButton confirmar;
    private Atalho atalho;

    public Renomear(Atalho atalho) {
        this.atalho = atalho;
        this.setTitle("Renomear " + atalho.getNome());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setUndecorated(true);
        this.setSize(400, 120);
        this.setLocationRelativeTo(null);
        this.getRootPane().setWindowDecorationStyle(1);

        nome = new JTextField();
        nome.setFont(new Font("Arial", Font.PLAIN, 18));
        nome.setPreferredSize(new Dimension(340, 50));
        nome.setText(atalho.getNome());
        nome.setSelectionStart(0);
        nome.setSelectionEnd(atalho.getNome().length());
        nome.addActionListener(e -> renomear());

        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(e -> cancelamento());
        confirmar = new JButton("Renomear");
        confirmar.addActionListener(e -> renomear());

        this.add(nome);
        this.add(cancelar);
        this.add(confirmar);
        this.setVisible(true);
    }

    public void cancelamento() {
        JOptionPane.showMessageDialog(null, "Bolha estourada, Operação Cancelada!");
        this.dispose();
    }
    
    public void renomear() {
        if (atalho.isHasComponent() && !nome.getText().isBlank()){
            atalho.setNome(nome.getText());
            if(atalho instanceof Pasta pasta) {
                if(pasta.getExplorar() != null) {
                    pasta.getExplorar().setTitle(nome.getText());
                    pasta.getExplorar().revalidate();
                    pasta.getExplorar().repaint();
                }
            }
            JOptionPane.showMessageDialog(null, "Arquivo renomeado com sucesso!");
            this.dispose();
        } 
        else {
            JOptionPane.showMessageDialog(null,
            "Insira algum nome para o Arquivo");
        }
    }
}

