package Modelo;

/**
 * Representa todo "tipo de conteúdo" que um atalho pode ter na área de
 * trabalho. Antes, cada tipo era uma subclasse de Atalho (Pasta, Snake,
 * Dinossauro, BlocoDeNotas, Browser); agora é apenas um valor de dado,
 * e quem decide "o que fazer" com cada tipo é o Servico.AplicativoLauncher.
 *
 * Adicionar um novo aplicativo deixa de ser "criar uma nova classe de
 * JPanel com mouse listeners duplicados" e passa a ser "adicionar uma
 * constante aqui + um caso no launcher".
 */
public enum TipoAtalho {
    VAZIO,
    PASTA,
    BLOCO_DE_NOTAS,
    NAVEGADOR,
    JOGO_COBRA,
    JOGO_DINOSSAURO
}
