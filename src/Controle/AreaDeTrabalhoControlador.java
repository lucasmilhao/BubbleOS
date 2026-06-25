package Controle;

import java.util.ArrayList;
import java.util.List;

/**
 * Dono do estado "global" da área de trabalho que antes vivia em campos
 * `static` da classe Atalho:
 *
 *   private static Atalho ativo;
 *   private static Atalho agarrado;
 *   private static TelaPrincipal tela;
 *
 * Esses três campos eram compartilhados por TODA instância de Atalho do
 * sistema (incluindo os que estavam dentro de pastas), o que é frágil:
 * qualquer atalho podia, acidentalmente, ler ou sobrescrever o estado de
 * outro, e era impossível ter duas áreas de trabalho independentes (por
 * exemplo, em testes).
 *
 * Aqui o mesmo estado existe, mas como atributos de instância de um
 * objeto único que é criado uma vez (na inicialização do sistema) e
 * injetado em cada AtalhoControlador. Isso é Inversão de Dependência:
 * em vez de cada Atalho "ir buscar" o estado global, o estado é entregue
 * a ele por quem o constrói.
 */
public class AreaDeTrabalhoControlador {

    private final List<AtalhoControlador> atalhos = new ArrayList<>();
    private AtalhoControlador selecionado;
    private AtalhoControlador arrastando;

    public void registrar(AtalhoControlador atalho) {
        atalhos.add(atalho);
    }

    public void selecionar(AtalhoControlador novoSelecionado) {
        if (selecionado != null && selecionado != novoSelecionado) {
            selecionado.desativar();
        }
        selecionado = novoSelecionado;
        selecionado.ativar();
    }

    public void notificarArraste(AtalhoControlador atalho) {
        this.arrastando = atalho;
    }

    public void notificarEntrada(AtalhoControlador alvo) {
        if (arrastando == null || arrastando == alvo) return;

        // Troca de posição entre o atalho arrastado e o atalho sobre o qual
        // o mouse entrou — mesma regra de negócio que existia em
        // Atalho.mouseEntered(), agora isolada e fácil de testar.
        int linhaArrastando = arrastando.getModelo().getLinha();
        int colunaArrastando = arrastando.getModelo().getColuna();
        int linhaAlvo = alvo.getModelo().getLinha();
        int colunaAlvo = alvo.getModelo().getColuna();

        arrastando.atualizarPosicaoNaGrade(linhaAlvo, colunaAlvo);
        alvo.atualizarPosicaoNaGrade(linhaArrastando, colunaArrastando);
    }

    public void soltar(AtalhoControlador atalho) {
        if (arrastando == atalho) {
            atalho.atualizarPosicaoNaGrade(atalho.getModelo().getLinha(), atalho.getModelo().getColuna());
            arrastando = null;
        }
    }

    public List<AtalhoControlador> getAtalhos() {
        return atalhos;
    }
}
