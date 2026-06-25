<div align="center">

<img src=".github/readme-assets/banner.png" alt="BubbleOS" width="640"/>

### Um sistema operacional falso (e divertido) construído do zero em Java

Área de trabalho com ícones arrastáveis, pastas, barra de tarefas, relógio, navegador web de verdade, bloco de notas e até jogos escondidos dentro do menu Iniciar.

<p>
  <img src="https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/JavaFX-21-blue?logo=java&logoColor=white" alt="JavaFX"/>
  <img src="https://img.shields.io/badge/Swing-UI-yellow" alt="Swing"/>
  <img src="https://img.shields.io/badge/plataforma-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey" alt="Plataformas"/>
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-brightgreen" alt="Status"/>
</p>

</div>

---

## 🫧 Sobre o projeto

**BubbleOS** é um sistema operacional de mentirinha que roda dentro de uma janela do seu computador. A ideia é simular a experiência de um desktop real — com área de trabalho, ícones, barra de tarefas, relógio e janelas de aplicativos — tudo construído manualmente em **Java**, sem nenhum framework de UI pronto para isso.

É um projeto acadêmico/pessoal focado em praticar **Orientação a Objetos**, manipulação de componentes gráficos com **Swing**, integração com **JavaFX** (para o navegador) e organização de um projeto de médio porte em camadas (`Visao`, `Aplicativos`, `Modelo`, `Controle`, `Sons`, `Jogos`).

> 💬 *"Bolhas! Operação cancelada."* — é o tipo de detalhe carinhoso que você encontra espalhado pelo código.

---

## ✨ Funcionalidades

| Recurso | Descrição |
|---|---|
| 🖥️ **Área de trabalho em grade** | Ícones organizados em uma grade de 16 colunas × 7 linhas, com suporte a **drag and drop** para reorganizar tudo onde quiser |
| 📁 **Pastas** | Crie pastas, arraste arquivos para dentro/fora delas e abra-as em uma janela de explorador própria |
| 🏷️ **Renomear ícones** | Clique com o botão direito em qualquer atalho e renomeie como preferir |
| 🎨 **Personalização de fundo** | Clique com o botão direito na área de trabalho para trocar o papel de parede por uma **cor sólida** ou por uma **imagem** do seu computador |
| 🪟 **Barra de tarefas** | Barra inferior fixa com botão **Iniciar** (menu com créditos do criador e opção de saída) e um **relógio** atualizado em tempo real |
| 📝 **Bloco de Notas** | Editor de texto completo: abrir/salvar `.txt`, trocar fonte, tamanho e cor do texto |
| 🌐 **BubbleBrowser** | Navegador web **funcional de verdade**, embutido via `JavaFX WebView` — carrega qualquer site |
| 🔊 **Feedback sonoro** | Efeitos sonoros (clique, erro) para dar aquela sensação de sistema "vivo" |

</br>

<div align="center">
<img src="https://github.com/lucasmilhao/bubbleos/tree/src/Imagens/bolha.png" width="64" height="64"/>
<img src=".github/readme-assets/icon-pasta.png" width="64" height="64"/>
<img src=".github/readme-assets/icon-notas.png" width="64" height="64"/>
<img src=".github/readme-assets/icon-cobra.png" width="64" height="64"/>
<img src=".github/readme-assets/icon-browser.png" width="64" height="64"/>
</div>

---

## 🎮 Jogos escondidos

O menu Iniciar do BubbleOS guarda dois clássicos reimplementados em Java puro:

<table>
<tr>
<td width="50%" valign="top">

### 🐍 Jogo da Cobrinha
O famoso **Snake**, com tabuleiro, comida e colisão — tudo desenhado manualmente com `Graphics2D`.

</td>
<td width="50%" valign="top">

### 🦖 Jogo do Dinossauro
Uma recriação do clássico **"jogo do dino" do Chrome**, com cenário em parallax, obstáculos e até skins alternativas (Megaman, Pikachu 👀).

<img src=".github/readme-assets/dino-preview.png" width="320"/>

</td>
</tr>
</table>

---

## 🏗️ Arquitetura

O projeto segue uma separação de responsabilidades inspirada em MVC:

```
BubbleOS/
├── lib/                      # Bibliotecas JavaFX (controls, fxml, web, swing...)
└── src/
    ├── App.java              # Ponto de entrada da aplicação
    │
    ├── Visao/                # Camada de interface (janelas, telas, ícones)
    │   ├── JanelaPrincipal   # Janela raiz do sistema (1280x720)
    │   ├── TelaPrincipal     # Área de trabalho (grade de ícones)
    │   ├── GerenciadorDeTarefas  # Barra de tarefas
    │   ├── Iniciar           # Botão e menu "Iniciar"
    │   ├── Relogio           # Relógio em tempo real
    │   ├── Atalho            # Classe-base abstrata de todo ícone (drag&drop, hover, menus)
    │   ├── Pasta / Explorar  # Pastas e seu explorador de arquivos
    │   ├── BlocoDeNotas / Browser / Snake / Dinossauro  # Atalhos para cada app
    │   └── Renomear          # Janela de renomear ícones
    │
    ├── Aplicativos/          # Aplicativos "de verdade" abertos pelos atalhos
    │   ├── Notas.java        # Bloco de notas completo
    │   └── Navegador.java    # Navegador com JavaFX WebView
    │
    ├── Jogos/
    │   ├── Cobra/            # Lógica do Snake
    │   └── Dinossauro/       # Lógica do jogo do dino
    │
    ├── Modelo/                # Classes de modelo (ex.: Bloco — obstáculos)
    ├── Controle/              # Camada de dados (DAO / conexão JDBC)
    ├── Sons/                  # Sistema de efeitos sonoros
    └── Imagens/               # Ícones, wallpapers e demais artes do sistema
```

**Destaques técnicos:**
- A classe **`Atalho`** é o coração da interatividade: implementa `MouseListener` para lidar com clique, arraste (drag), hover e menus de contexto — toda a área de trabalho é, na prática, uma matriz `Atalho[][]`.
- O **`BubbleBrowser`** mistura **Swing + JavaFX** na mesma janela usando `JFXPanel`, permitindo embutir um motor de navegação web completo (`WebView`/`WebEngine`) dentro de uma janela Swing tradicional.
- Janelas usam `setUndecorated(true)` + `getRootPane().setWindowDecorationStyle(...)` para um visual mais "encorpado" e customizado, fugindo da decoração padrão do sistema operacional hospedeiro.

---

## 🚀 Como executar

### Pré-requisitos
- **JDK 17+** instalado
- **JavaFX SDK** (o projeto foi testado com a versão 21) — necessário pois o JDK não inclui mais o JavaFX por padrão

### Rodando pela linha de comando

```bash
# 1. Clone o repositório
git clone https://github.com/luquinhasgalante/BubbleOS.git
cd BubbleOS

# 2. Compile (ajuste o caminho do JavaFX SDK para o seu sistema)
javac --module-path "CAMINHO/PARA/javafx-sdk-21/lib" \
      --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.web \
      -d bin -cp lib/* $(find src -name "*.java")

# 3. Copie os recursos (imagens e sons) para a pasta de saída
cp -r src/Imagens bin/
cp -r src/Sons bin/
cp -r src/Jogos/Dinossauro/img bin/Jogos/Dinossauro/

# 4. Execute
java --module-path "CAMINHO/PARA/javafx-sdk-21/lib" \
     --add-modules javafx.controls,javafx.fxml,javafx.swing,javafx.web \
     -cp bin:lib/* App
```

### Rodando pelo VS Code / IntelliJ

1. Abra a pasta do projeto na sua IDE com suporte a Java (extensão *Java Extension Pack* no VS Code, por exemplo).
2. Baixe o [JavaFX SDK](https://gluonhq.com/products/javafx/) e ajuste o caminho no arquivo `launch.json`, na propriedade `vmArgs`.
3. Rode a classe `App.java` — ela é o ponto de entrada do sistema.

> 💡 O arquivo `launch.json` já vem com os argumentos de módulo (`--module-path` / `--add-modules`) necessários para o VS Code — só falta apontar para o local correto do seu JavaFX SDK.

---

## 🖱️ Como usar

- **Arrastar ícones:** clique e arraste qualquer ícone para reposicioná-lo na grade.
- **Abrir um app:** clique uma vez para selecionar, clique novamente (ou duplo clique) para abrir.
- **Menu de contexto:** botão direito sobre um ícone para **renomear** ou mover para a tela principal; botão direito na área vazia para **trocar o papel de parede**.
- **Criar uma pasta:** arraste um ícone em cima de outro para agrupá-los (ou use as opções disponíveis no atalho de pasta).
- **Menu Iniciar:** clique no botão **Iniciar**, no canto inferior esquerdo, para acessar créditos do criador e a opção de sair do sistema.

---

## 🛠️ Tecnologias utilizadas

- **Java** (Swing/AWT) — toda a estrutura de janelas, componentes e desenho 2D customizado
- **JavaFX** (`WebView`, `JFXPanel`) — motor do navegador embutido
- **Java Sound API** — efeitos sonoros do sistema
- **JDBC / MySQL** — camada de conexão preparada para persistência de dados (`ConexaoDAO`)

---

## 👤 Autor

Desenvolvido por **Lucas** ([@luquinhasgalante](https://github.com/luquinhasgalante)) como projeto de estudo em Java.

Saiba mais sobre o criador direto pelo próprio menu **Iniciar** do BubbleOS, na opção *"Ver sobre o criador"* — sim, o sistema tem um easter egg que abre o Instagram do autor no próprio BubbleBrowser. 😄

---

<div align="center">

Feito com 🫧 e muitas linhas de `Graphics2D`.

</div>
