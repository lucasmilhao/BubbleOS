package Visao;

import Modelo.AtalhoModelo;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Pequeno exemplo de como uma janela auxiliar (que antes recebia o
 * "Atalho" inteiro, View+Controller+Model juntos) passa a depender só do
 * que ela realmente precisa: o Modelo (para ler/gravar o nome) e a View
 * (só para pedir um repaint depois de mudar o nome).
 *
 * Note que esta janela não conhece AtalhoControlador nem
 * AreaDeTrabalhoControlador — ela não precisa saber nada sobre seleção,
 * arraste ou troca de posição. Esse é o tipo de redução de acoplamento
 * que a separação em camadas viabiliza.
 */
public class Renomear extends JFrame {

    private final AtalhoModelo modelo;
    private final AtalhoView view;
    private final JTextField nome;

    public Renomear(AtalhoModelo modelo, AtalhoView view) {
        this.modelo = modelo;
        this.view = view;

        this.setTitle("Renomear " + modelo.getNome());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setUndecorated(true);
        this.setSize(400, 120);
        this.setLocationRelativeTo(null);
        this.getRootPane().setWindowDecorationStyle(1);

        nome = new JTextField(modelo.getNome());
        nome.setFont(new Font("Arial", Font.PLAIN, 18));
        nome.setPreferredSize(new Dimension(340, 50));

        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");

        confirmar.addActionListener(e -> confirmar());
        cancelar.addActionListener(e -> dispose());

        this.add(nome);
        this.add(confirmar);
        this.add(cancelar);
        this.setVisible(true);
    }

    private void confirmar() {
        String novoNome = nome.getText().trim();

        if (novoNome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode ficar em branco.");
            return;
        }

        modelo.setNome(novoNome);
        view.repaint();
        dispose();
    }
}
