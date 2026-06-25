package Servico;

import Aplicativos.Navegador;
import Aplicativos.Notas;
import Jogos.Cobra.SnakeFrame;
import Jogos.Dinossauro.Game;
import Modelo.AtalhoModelo;
import Visao.Explorar;

/**
 * Implementação concreta do AplicativoLauncher.
 *
 * Esta classe é a ÚNICA do sistema que conhece, ao mesmo tempo, o Modelo
 * (AtalhoModelo/TipoAtalho) e as classes concretas dos aplicativos
 * (Notas, Navegador, SnakeFrame, Game, Explorar). Isso concentra o
 * acoplamento em um único lugar, em vez de espalhá-lo pelas 5 subclasses
 * de Atalho que existiam antes.
 */
public class AplicativoLauncherPadrao implements AplicativoLauncher {

    @Override
    public void abrir(AtalhoModelo atalho) {
        switch (atalho.getTipo()) {
            case BLOCO_DE_NOTAS -> new Notas(atalho.getArquivoAssociado());
            case NAVEGADOR -> new Navegador(atalho.getUrlAssociada());
            case JOGO_COBRA -> new SnakeFrame();
            case JOGO_DINOSSAURO -> new Game();
            case PASTA -> new Explorar(atalho);
            case VAZIO -> { /* nada para abrir */ }
        }
    }
}
