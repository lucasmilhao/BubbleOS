package Visao;

import Sons.Som;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public final class Pasta extends Atalho {
    
    private ArrayList<Atalho> atalhos = new ArrayList<>();
    private int contadorI = 0;
    private int contadorJ = 0;
    private Explorar explorar;
    
    public Pasta() {}

    public Pasta(int i, int j) {
        super(i, j);
        this.explorar = null;
        this.setImagem(new ImageIcon(getClass().getResource("/Imagens/Pasta.png")).getImage());
        this.setOpaque(false);
        this.setHasComponent(true);
        this.setNome("Nova Pasta");
    }

    @Override
    public void abrirMenu(MouseEvent e) {
        super.abrirMenu(e);

        JMenuItem adicionarItem = new JMenuItem("Adicionar à pasta");
        adicionarItem.addActionListener(e1 -> adicionarArquivo());
        JMenuItem abrir = new JMenuItem("Abrir");
        abrir.addActionListener(e1 -> abrir());
        this.getMenu().setPreferredSize(new Dimension(120, 50));
        this.getMenu().add(adicionarItem);
        this.getMenu().add(abrir);

        this.getMenu().show(this, e.getX(), e.getY());
    }

    public void adicionarArquivo() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione sua imagem");
        chooser.setCurrentDirectory(new File("."));

        int resultado = chooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            String extensao = arquivo.getName().substring(arquivo.getName().lastIndexOf('.') + 1);
            System.out.println(extensao);


            if(
                extensao.equals("txt")|| 
                extensao.equals("java")|| 
                extensao.equals("me")
            ) {
                BlocoDeNotas notas = new BlocoDeNotas(contadorI, contadorJ, arquivo);
                notas.setNome(arquivo.getName());
                notas.setPasta(this);
                this.atalhos.add(notas);
                contadorJ++;

                if(this.contadorJ % 8 == 0) {
                    this.contadorI++;
                    this.contadorJ = 0;
                }
            }
            else if(
                extensao.equals("png") || 
                extensao.equals("jpeg") || 
                extensao.equals("jpg")) {
                Image imagem = new ImageIcon(arquivo.getAbsolutePath()).getImage();
                Imagem atalho = new Imagem(contadorI, contadorJ, this);
                atalho.setPasta(this);
                atalho.setImagem(imagem);
                atalho.setNome(arquivo.getName());

                this.atalhos.add(atalho);
                contadorJ++;

                if(this.contadorJ % 8 == 0) {
                    this.contadorI++;
                    this.contadorJ = 0;
                }
            }
            else {
                Som.padrao(Som.getErro());
                JOptionPane.showMessageDialog(null, "Bolhuras... Não suportamos esse formato");
            }


            if(explorar != null) {
                explorar.exibirAtalhos();
                explorar.revalidate();
                explorar.repaint();
            }
            
        } else {
            Som.padrao(Som.getErro());
            JOptionPane.showMessageDialog(null, "Bolhas! Operação cancelada.");
        }
    }

    public void abrir() {
        this.setRun(true);
        this.explorar = new Explorar(this);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) abrir();
        repaint();
    }

    public ArrayList<Atalho> getAtalhos() {
        return atalhos;
    }

    public void setAtalhos(ArrayList<Atalho> atalhos) {
        this.atalhos = atalhos;
    }

    public int getContadorI() {
        return contadorI;
    }

    public void setContadorI(int contadorI) {
        this.contadorI = contadorI;
    }

    public int getContadorJ() {
        return contadorJ;
    }

    public void setContadorJ(int contadorJ) {
        this.contadorJ = contadorJ;
    }

    public Explorar getExplorar() {
        return explorar;
    }

    public void setExplorar(Explorar explorar) {
        this.explorar = explorar;
    }   
}
