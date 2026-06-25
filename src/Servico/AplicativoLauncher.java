package Servico;

import Modelo.AtalhoModelo;

/**
 * Abstração (Strategy) para "abrir o que esse atalho representa".
 *
 * Isso é o que substitui o método abstrato `abrir()` que cada subclasse
 * de Atalho (Snake, Dinossauro, BlocoDeNotas, Browser, Pasta) precisava
 * implementar. Em vez de herança, usamos composição: o Controlador recebe
 * um AplicativoLauncher e delega a ele a decisão de qual janela abrir.
 *
 * Vantagem prática: para adicionar um novo aplicativo ao BubbleOS, não é
 * preciso mais criar uma nova classe de ícone — só uma nova constante em
 * TipoAtalho e um novo `case` na implementação deste launcher.
 */
public interface AplicativoLauncher {
    void abrir(AtalhoModelo atalho);
}
