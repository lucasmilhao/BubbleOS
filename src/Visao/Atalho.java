package Visao;

import Sons.Som;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

public abstract class Atalho extends JPanel implements MouseListener {
    
    private final int ALTURA = 85;
    private final int LARGURA = 80;
    private Point offset = null;
    private Pasta pasta = null;
    private Image imagem;
    private MouseAdapter mouseAdapter;
    private int LARGURA_IMAGEM;
    private int ALTURA_IMAGEM;
    private int i;
    private int j;
    private int posicaoInicialX;
    private int posicaoInicialY;
    private boolean active;
    private boolean hasComponent;
    private boolean mouseIn;
    private boolean run;
    private static Atalho ativo;
    private static Atalho agarrado;
    private static TelaPrincipal tela;
    private JPopupMenu menu;
    private String nome;


    public Atalho(){}

    public Atalho(int i, int j) {
        initComponents(i, j);
        this.setOpaque(false);
    }

    private void initComponents(int i, int j) {
        this.i = i;
        this.j = j;
        this.posicaoInicialX = (j * LARGURA);
        this.posicaoInicialY = (i * ALTURA);

        this.setBounds(this.posicaoInicialX, this.posicaoInicialY, LARGURA, ALTURA);
        this.LARGURA_IMAGEM = 33;
        this.ALTURA_IMAGEM = 53;
        this.active = false;
        this.hasComponent = false;
        this.mouseIn = false;
        this.run = false;
        this.nome = "";
        Atalho.ativo = null;
        

        this.mouseAdapter = new MouseAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if(offset != null) {
                    if(Atalho.this.isHasComponent()) {

                        Atalho.setAgarrado(Atalho.this);
                        
                        int x = getX() + e.getX() - offset.x;
                        int y = getY() + e.getY() - offset.y;
                        setLocation(x, y);
                        setActive(false);
                    }
                }
            }
        };

        
        this.addMouseListener(this);
        this.addMouseMotionListener(mouseAdapter);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        pintarQuandoPressionado(g2d);
        desenharHover(g2d);
        desenharComponente(g2d);
    }

    private void desenharComponente(Graphics2D g2d) {

        if(imagem != null) g2d.drawImage(this.getImagem(),(getWidth() / 2) - (LARGURA_IMAGEM / 2),6, LARGURA_IMAGEM, ALTURA_IMAGEM, null);
        g2d.setFont(new Font("Arial", Font.PLAIN, 9));
        if(!nome.isBlank()){
            g2d.setColor(Color.black);
            g2d.drawString(nome, (getWidth() - g2d.getFontMetrics().stringWidth(nome)) / 2, getHeight() - 4);
            g2d.setColor(Color.white);
            g2d.drawString(nome, (getWidth() - g2d.getFontMetrics().stringWidth(nome)) / 2, getHeight() - 5);

        } 
    }

    public void pintarQuandoPressionado(Graphics2D g2d) {

        if(this.isActive()) {
            g2d.setColor(new Color(0, 0, 128, 64));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            if(run) g2d.fillRect((getWidth() / 2) - (LARGURA_IMAGEM / 2), 6, LARGURA_IMAGEM, ALTURA_IMAGEM);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() -1 );
        }

    }

    private void desenharHover(Graphics2D g2d) {
        if(this.isHasComponent()) {

            if(this.getMouseIn()) {
                g2d.setColor(new Color(255, 255, 255, 64));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(Color.white);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        }
    }

    public void abrirMenu(MouseEvent e) {
        Som.padrao(Som.getClick());

        menu = new JPopupMenu();
        JMenuItem renomear = new JMenuItem("Renomear");
        renomear.addActionListener(e1 -> new Renomear(this));

        if(!(getParent() instanceof TelaPrincipal)) {
            JMenuItem adicionar = new JMenuItem("Adicionar a tela principal");
            System.out.println(tela);
            adicionar.addActionListener(e1 -> irTelaPrincipal());
            menu.add(adicionar);
            menu.setPreferredSize(new Dimension(160, 70));
        }

        menu.add(renomear);
    }

    private void recalcularBounds(int i, int j) {
        
        posicaoInicialX = (j * LARGURA);
        posicaoInicialY = (i * ALTURA);
        System.out.println("Posição inicialX = " + posicaoInicialX);
        System.out.println("Posição inicialY = " + posicaoInicialY);

        this.setBounds(posicaoInicialX, posicaoInicialY, LARGURA, ALTURA);
        revalidate();
        repaint();
    }


    private void pressionado() {

        if(this.isActive()) {
            this.setRun(true);
        } 
        else {
            
            if(ativo != null) {
                ativo.setActive(false);
                ativo.repaint();
            }

            if(this.isHasComponent()) {
                ativo = this;
                this.setRun(false);
                this.setActive(true);
            }
            repaint();
        }
    }

    public void irTelaPrincipal() {
        int contadorI = 0;
        int contadorJ = 0;
        boolean encontrado = false;

        for(Atalho[] list : tela.getArray()) {
            for(Atalho a : list) {
                if(!a.hasComponent) {
                    Pasta p = this.getPasta();
                    p.getAtalhos().remove(this);
                    if(p.getContadorJ() > 0) p.setContadorJ(p.getContadorJ() - 1);
                    else p.setContadorI(p.getContadorI() - 1);
                    recalcularBounds(contadorI, contadorJ);
                    encontrado = true;
                    break;
                }
                contadorJ++;
            }
            if(encontrado) break;
            contadorI++;
        }
        tela.adicionarAtalho(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            pressionado();
            offset = e.getPoint();

            if(getParent() instanceof TelaPrincipal tela) {
                tela.setLayer(this, TelaPrincipal.getGRAB_LAYER());
            }
        }

        if(e.getButton() == MouseEvent.BUTTON3) {
            
            this.setRun(false);
        
            if(!this.hasComponent) {
                Som.padrao(Som.getClick());
                JPopupMenu menu = new JPopupMenu();
                JMenu mudarFundo = new JMenu("Mudar Plano de fundo");
                JMenuItem corSolida = new JMenuItem("Selecionar cor Sólida");
                JMenuItem escolhaImagem = new JMenuItem("Selecionar Imagem");
                escolhaImagem.addActionListener(e1 -> salvarImagem());
                corSolida.addActionListener(e1 -> selecionarCor());
                
                mudarFundo.add(corSolida);
                mudarFundo.add(escolhaImagem);
                menu.add(mudarFundo);
                
                int x = e.getX();
                int y = e.getY();
                
                menu.show(this , x, y);
            }
            else {
                abrirMenu(e);
                menu.show(this , e.getX(), e.getY());
            }
        }
    }

    private void selecionarCor() {
        Color cor = JColorChooser.showDialog(null, "Escolha a cor de fundo", Color.BLACK);
        if(getParent() instanceof TelaPrincipal tela) {
            tela.selecionarCor(cor);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(Atalho.getAgarrado() != null) {

            Atalho.getAgarrado().setBounds(
                agarrado.getPosicaoInicialX(),
                agarrado.getPosicaoInicialY(),
                agarrado.getLARGURA(),
                agarrado.getALTURA()
            );
            
            Atalho.setAgarrado(null);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setMouseIn(true);

        if(getParent() instanceof TelaPrincipal tela) {

            if(Atalho.getAgarrado() != null && this != Atalho.getAgarrado()) {
                int finalX = agarrado.getPosicaoInicialX();
                int finalY = agarrado.getPosicaoInicialY();
                int finalI = agarrado.getI();
                int finalJ = agarrado.getJ();
                agarrado.setPosicaoInicialX(this.getPosicaoInicialX());
                agarrado.setPosicaoInicialY(this.getPosicaoInicialY());
                agarrado.setI(this.getI());
                agarrado.setJ(this.getJ());
                this.setPosicaoInicialX(finalX);
                this.setPosicaoInicialY(finalY);
                this.setI(finalI);
                this.setJ(finalJ);
                this.setBounds( getPosicaoInicialX(), getPosicaoInicialY(), getLARGURA(), getALTURA());
            }
        }
        revalidate();
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setMouseIn(false);
        repaint();
    }

    public void salvarImagem() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecione sua imagem");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(new File("."));

        FileNameExtensionFilter filtro =
        new FileNameExtensionFilter("Apenas imagens", "jpg", "jpeg", "png");
        chooser.setFileFilter(filtro);

        int resultado = chooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = chooser.getSelectedFile();
            Image imagemFundo = new ImageIcon(arquivo.getAbsolutePath()).getImage();
            if(getParent() instanceof TelaPrincipal tela) {
                tela.setPlanoFundo(imagemFundo);
                tela.setCorSolida(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bolhas! Operação cancelada.");
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isHasComponent() {
        return hasComponent;
    }

    public void setMouseIn(boolean in) {
        this.mouseIn = in;
    }

    public boolean getMouseIn() {
        return this.mouseIn;
    }

    public void setHasComponent(boolean hasComponent) {
        this.hasComponent = hasComponent;
    }

    public Image getImagem() {
        return imagem;
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }

    public int getLARGURA_IMAGEM() {
        return LARGURA_IMAGEM;
    }

    public void setLARGURA_IMAGEM(int LARGURA_IMAGEM) {
        this.LARGURA_IMAGEM = LARGURA_IMAGEM;
    }

    public int getALTURA_IMAGEM() {
        return ALTURA_IMAGEM;
    }

    public void setALTURA_IMAGEM(int ALTURA_IMAGEM) {
        this.ALTURA_IMAGEM = ALTURA_IMAGEM;
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getALTURA() {
        return ALTURA;
    }

    public int getLARGURA() {
        return LARGURA;
    }

    public Point getOffset() {
        return offset;
    }

    public void setOffset(Point offset) {
        this.offset = offset;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getPosicaoInicialX() {
        return posicaoInicialX;
    }

    public void setPosicaoInicialX(int posicaoInicialX) {
        this.posicaoInicialX = posicaoInicialX;
    }

    public int getPosicaoInicialY() {
        return posicaoInicialY;
    }

    public void setPosicaoInicialY(int posicaoInicialY) {
        this.posicaoInicialY = posicaoInicialY;
    }

    public static Atalho getAtivo() {
        return ativo;
    }

    public static void setAtivo(Atalho ativo) {
        Atalho.ativo = ativo;
    }

    public static Atalho getAgarrado() {
        return agarrado;
    }

    public static void setAgarrado(Atalho agarrado) {
        Atalho.agarrado = agarrado;
    }

    public MouseAdapter getMouseAdapter() {
        return mouseAdapter;
    }

    public void setMouseAdapter(MouseAdapter mouseAdapter) {
        this.mouseAdapter = mouseAdapter;
    }

    public JPopupMenu getMenu() {
        return menu;
    }

    public void setMenu(JPopupMenu menu) {
        this.menu = menu;
    }

    public static TelaPrincipal getTela() {
        return tela;
    }

    public static void setTela(TelaPrincipal tela) {
        Atalho.tela = tela;
    }

    public Pasta getPasta() {
        return pasta;
    }

    public void setPasta(Pasta pasta) {
        this.pasta = pasta;
    }

    

}

