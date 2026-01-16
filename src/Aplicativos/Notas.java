package Aplicativos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notas extends JFrame implements ActionListener {
    
    private JTextArea textArea;
    private final JScrollPane scroll;
    private JSpinner spinner;
    private final JLabel fontLabel;
    private JButton mudarCor;
    private JComboBox<String> fontesBox; 
    private JMenuBar menuBar;
    private JMenu arquivoMenu;
    private JMenuItem abrirItem;
    private JMenuItem salvarItem;
    private JMenuItem sairItem;
    private File arquivo;

    public Notas(File arquivo) {
        this.setTitle("Bloco de notas");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(400, 550);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(1);

        this.arquivo = arquivo;
        
        this.textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));


        this.scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(380, 480));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel("Fonte: ");

        spinner = new JSpinner();
        spinner.setPreferredSize(new Dimension(50, 24));
        spinner.setValue(20);
        spinner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                // TODO Auto-generated method stub
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) spinner.getValue()));
            }
            
        });

        this.mudarCor = new JButton("Cor");
        mudarCor.setPreferredSize(new Dimension(60, 24));
        mudarCor.addActionListener(this);

        String[] fontes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontesBox = new JComboBox<>(fontes);
        fontesBox.addActionListener(this);
        fontesBox.setSelectedItem("Arial");
        fontesBox.setPreferredSize(new Dimension(80, 24));


        menuBar = new JMenuBar();
        arquivoMenu = new JMenu("Arquivo");
        salvarItem = new JMenuItem("Salvar");
        abrirItem = new JMenuItem("Abrir");
        sairItem = new JMenuItem("Sair");

        salvarItem.addActionListener(this);
        abrirItem.addActionListener(this);
        sairItem.addActionListener(this);

        arquivoMenu.add(salvarItem);
        arquivoMenu.add(abrirItem);
        arquivoMenu.add(sairItem);

        menuBar.add(arquivoMenu);

        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(spinner);
        this.add(mudarCor);
        this.add(fontesBox);
        this.add(scroll);

        abrirArquivo();
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == mudarCor) {
            Color cor = JColorChooser.showDialog(null, "Escolha a cor para seu texto!", Color.BLACK);

            textArea.setForeground(cor);
        }

        if(e.getSource() == fontesBox) {
            textArea.setFont(new Font(fontesBox.getSelectedItem().toString(), Font.PLAIN, (int) spinner.getValue()));
        }

        if(e.getSource() == abrirItem) {
            
            JFileChooser chooser = new JFileChooser();

            chooser.setCurrentDirectory(new File("."));

            FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Arquivo de texto", "txt");
            
            chooser.setFileFilter(filter);

            int resposta = chooser.showSaveDialog(null);

            if(resposta == JFileChooser.APPROVE_OPTION) {
                this.arquivo = new File(chooser.getSelectedFile().getAbsolutePath());
                Scanner escreverArquivo = null;

                try {
                    escreverArquivo = new Scanner(arquivo);
                    if(arquivo.isFile()) {
                        while(escreverArquivo.hasNextLine()) {
                            String linha = escreverArquivo.nextLine() + "\n";
                            textArea.append(linha);
                        }
                    }
                } catch(FileNotFoundException e1) {
                    e1.getMessage();
                }
                finally {
                    escreverArquivo.close();
                }

            }
        }

        if(e.getSource() == salvarItem) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            int resposta = chooser.showSaveDialog(null);

            if(resposta == JFileChooser.APPROVE_OPTION) {
                File arquivo;
                PrintWriter arquivoSaida = null;

                arquivo = new File(chooser.getSelectedFile().getAbsolutePath());
                try {
                    arquivoSaida = new PrintWriter(arquivo);
                    arquivoSaida.println(textArea.getText());
                } catch (Exception e1) {
                    e1.getMessage();
                }
                finally {
                    arquivoSaida.close();
                }
            }

        }

        if(e.getSource() == sairItem) {
            this.dispose();
        }
    }

    public void abrirArquivo() {
        if(this.arquivo != null) {
           Scanner escreverArquivo = null;

            try {
                escreverArquivo = new Scanner(arquivo);
                if(arquivo.isFile()) {
                    while(escreverArquivo.hasNextLine()) {
                        String linha = escreverArquivo.nextLine() + "\n";
                        textArea.append(linha);
                    }
                }
            } catch(FileNotFoundException e1) {
                e1.getMessage();
            }
            finally {
                escreverArquivo.close();
            }
        }
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public void setSpinner(JSpinner spinner) {
        this.spinner = spinner;
    }

    public JButton getMudarCor() {
        return mudarCor;
    }

    public void setMudarCor(JButton mudarCor) {
        this.mudarCor = mudarCor;
    }

    public JComboBox<String> getFontesBox() {
        return fontesBox;
    }

    public void setFontesBox(JComboBox<String> fontesBox) {
        this.fontesBox = fontesBox;
    }

    public JMenu getArquivoMenu() {
        return arquivoMenu;
    }

    public void setArquivoMenu(JMenu arquivoMenu) {
        this.arquivoMenu = arquivoMenu;
    }

    public JMenuItem getAbrirItem() {
        return abrirItem;
    }

    public void setAbrirItem(JMenuItem abrirItem) {
        this.abrirItem = abrirItem;
    }

    public JMenuItem getSalvarItem() {
        return salvarItem;
    }

    public void setSalvarItem(JMenuItem salvarItem) {
        this.salvarItem = salvarItem;
    }

    public JMenuItem getSairItem() {
        return sairItem;
    }

    public void setSairItem(JMenuItem sairItem) {
        this.sairItem = sairItem;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }


}
