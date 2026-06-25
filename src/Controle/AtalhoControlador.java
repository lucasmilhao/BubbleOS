package Controle;

import Modelo.AtalhoModelo;
import Servico.AplicativoLauncher;
import Sons.Som;
import Visao.AtalhoView;
import Visao.Renomear;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * CONTROLADOR (C do MVC) de um único atalho.
 *
 * É aqui que mora a lógica que antes estava espalhada dentro da própria
 * classe Atalho: o que fazer ao clicar, ao arrastar, ao abrir o menu de
 * contexto. A diferença essencial é que o Controlador NÃO estende JPanel
 * e NÃO desenha nada — ele só orquestra o Modelo e a View.
 *
 * O estado "qual atalho está ativo" e "qual está sendo arrastado" deixou
 * de ser `static` (como era em Atalho.ativo / Atalho.agarrado) e passou a
 * viver no AreaDeTrabalhoControlador, que é uma instância normal injetada
 * aqui — isso elimina acoplamento oculto entre todos os atalhos do
 * sistema e torna a classe testável.
 */
public class AtalhoControlador {

    private final AtalhoModelo modelo;
    private final AtalhoView view;
    private final AreaDeTrabalhoControlador areaControlador;
    private final AplicativoLauncher launcher;
    private Point offsetArraste;

    public AtalhoControlador(AtalhoModelo modelo,
                              AtalhoView view,
                              AreaDeTrabalhoControlador areaControlador,
                              AplicativoLauncher launcher) {
        this.modelo = modelo;
        this.view = view;
        this.areaControlador = areaControlador;
        this.launcher = launcher;
        registrarEventosDoMouse();
    }

    private void registrarEventosDoMouse() {
        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                offsetArraste = e.getPoint();

                if (e.getButton() == MouseEvent.BUTTON1) {
                    areaControlador.selecionar(AtalhoControlador.this);
                }
                if (e.getButton() == MouseEvent.BUTTON3 && modelo.isOcupado()) {
                    abrirMenuDeContexto(e);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!modelo.isOcupado() || offsetArraste == null) return;

                int x = view.getX() + e.getX() - offsetArraste.x;
                int y = view.getY() + e.getY() - offsetArraste.y;
                view.setLocation(x, y);
                areaControlador.notificarArraste(AtalhoControlador.this);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                areaControlador.soltar(AtalhoControlador.this);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                view.setMouseDentro(true);
                areaControlador.notificarEntrada(AtalhoControlador.this);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                view.setMouseDentro(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && modelo.isOcupado()) {
                    Som.padrao(Som.getClick());
                    launcher.abrir(modelo);
                }
            }
        };

        view.addMouseListener(adapter);
        view.addMouseMotionListener(adapter);
    }

    private void abrirMenuDeContexto(MouseEvent e) {
        Som.padrao(Som.getClick());

        JPopupMenu menu = new JPopupMenu();
        JMenuItem renomear = new JMenuItem("Renomear");
        renomear.addActionListener(ev -> new Renomear(modelo, view));
        menu.add(renomear);

        menu.show(view, e.getX(), e.getY());
    }

    /** Chamado pelo AreaDeTrabalhoControlador quando este atalho passa a ser o selecionado. */
    public void ativar() {
        view.setAtivo(true);
    }

    /** Chamado pelo AreaDeTrabalhoControlador quando outro atalho é selecionado. */
    public void desativar() {
        view.setAtivo(false);
    }

    public void atualizarPosicaoNaGrade(int linha, int coluna) {
        modelo.moverPara(linha, coluna);
        view.atualizarPosicao();
    }

    public AtalhoModelo getModelo() {
        return modelo;
    }

    public AtalhoView getView() {
        return view;
    }
}
