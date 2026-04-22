# ⚙️ Análise de Treliça com Métodos Numéricos

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17-orange?style=for-the-badge&logo=openjfx&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven&logoColor=white)
![MVC](https://img.shields.io/badge/Arquitetura-MVC-brightgreen?style=for-the-badge)

Aplicação desktop de engenharia para a disciplina de Cálculo Numérico, desenvolvida em **Java 17** com **JavaFX**. O software resolve sistemas de equações lineares que modelam as forças internas em estruturas de treliça, aplicando e comparando métodos numéricos diretos e iterativos.

O projeto segue o padrão arquitetural **Model-View-Controller (MVC)** e foi construído com foco em clareza de código, usabilidade e apresentação didática dos resultados.

---

## 🖥️ Demonstração da Aplicação

<p align="center">
  <img src="docs/demo-trelica.gif" alt="Demonstração da Aplicação" width="100%" style="border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.2);" />
</p>

### 🔍 O que a demonstração mostra?

O GIF acima ilustra o fluxo completo de uso e a capacidade de análise comparativa da ferramenta:

1.  **Visão Geral:** A interface exibe o sistema de equações pré-definido e os controles para execução dos métodos.
2.  **Método Direto (Gauss):** Com um clique, o método de **Eliminação de Gauss** é executado. A solução é calculada instantaneamente e exibida nos campos de resultado, juntamente com o tempo de execução em milissegundos.
3.  **Método Iterativo (Gauss-Seidel):** Em seguida, o método de **Gauss-Seidel** é acionado. Além da solução final, a aplicação preenche:
    - Uma **tabela detalhada** com o estado das variáveis (F1, F2, F3, F4) e o erro relativo a cada iteração.
    - Um **gráfico de convergência** que plota o erro (em escala logarítmica) em função das iterações, provando visualmente a eficiência do método.

💡 **O poder da comparação:** A ferramenta permite analisar não apenas a solução, mas também a *performance* de cada método. Observa-se que, para este sistema, Gauss-Seidel converge rapidamente, oferecendo uma alternativa viável ao método direto, com a vantagem de fornecer uma trilha de auditoria de convergência.

---

## 🎯 Objetivo e Contexto do Problema

O projeto visa resolver o sistema de equações lineares que representa o equilíbrio estático de forças em uma estrutura de treliça. A solução do sistema determina as forças de tração ou compressão (F1, F2, F3, F4) em cada barra da estrutura.

#### Sistema de Equações Modelado:
```
[ 4  -1   0   0 ] [ F1 ]   [ 50 ]
[ -1  4  -1   0 ] [ F2 ] = [  0 ]
[ 0  -1   4  -1 ] [ F3 ]   [ 80 ]
[ 0   0  -1   3 ] [ F4 ]   [  0 ]
```

---

## ✨ Funcionalidades e Diferenciais Técnicos

- **Interface Gráfica Reativa (JavaFX):** Uma UI limpa e moderna que apresenta dados complexos de forma clara.
- **Implementação Manual dos Métodos:** Os algoritmos de **Eliminação de Gauss** e **Gauss-Seidel** foram implementados do zero, sem o uso de bibliotecas externas de matemática, para fins didáticos e de aprendizado.
- **Análise de Convergência (Gauss-Seidel):**
    - **Tabela de Iterações:** Exibe a evolução completa do cálculo, passo a passo.
    - **Gráfico de Erro vs. Iteração:** Visualização logarítmica da taxa de convergência, essencial para entender a eficiência do método.
    - **Verificação do Critério de Sassenfeld:** O sistema automaticamente checa se a matriz é diagonalmente dominante (critério das linhas), informando o usuário sobre a garantia de convergência.
- **Métricas de Performance:** Cálculo e exibição do tempo de execução de cada método, permitindo uma comparação direta de desempenho.
- **Arquitetura MVC Robusta:** Código organizado e desacoplado:
    - **Model:** Classes que representam os dados (`SistemaLinear`, `ResultadoMetodo`).
    - **View:** Arquivos FXML e CSS que definem a interface.
    - **Controller:** Orquestra a interação entre a View e a lógica no Model/Service.
- **Configuração de Tolerância:** O usuário pode ajustar o critério de parada (erro tolerável) para o método iterativo.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Descrição |
| :--- | :--- |
| ☕ **Java 17** | Versão LTS da linguagem, com suporte a Records para DTOs imutáveis. |
| 🎨 **JavaFX 17** | Framework para a construção da interface gráfica moderna e multiplataforma. |
| 📦 **Apache Maven** | Gerenciamento de dependências e automação do build do projeto. |
| 🏛️ **Padrão MVC** | Arquitetura que promove a separação de responsabilidades e a manutenibilidade. |
| 📈 **JavaFX Charts**| Componente nativo para a criação de gráficos de alta qualidade. |

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos
- **JDK (Java Development Kit)** - Versão 17 ou superior.
- **Apache Maven** - Versão 3.8.x ou superior, configurado na variável de ambiente `PATH`.
- **Git** - Para clonar o repositório.

### 1️⃣ Clonar o Repositório
```bash
git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
cd SEU-REPOSITORIO
```

### 2️⃣ Executar com Maven
O projeto já está configurado com o plugin do JavaFX para Maven. Execute o seguinte comando no terminal, na raiz do projeto:

```bash
mvn clean javafx:run
```
O Maven cuidará de baixar as dependências, compilar o código e iniciar a aplicação gráfica.

---

## 🏗️ Estrutura do Projeto

O projeto segue a estrutura padrão de um projeto Maven e adota o padrão **Package-by-Layer**, separando as responsabilidades em pacotes bem definidos.

```text
src/main/
├── java/br/edu/projeto4
│   ├── MainApplication.java       // Ponto de entrada da aplicação JavaFX
│   ├── controller
│   │   └── MainController.java    // Lógica de controle da interface
│   ├── model
│   │   ├── IteracaoSistemaLinear.java // DTO para uma linha da tabela de iteração
│   │   ├── ResultadoMetodo.java   // DTO para o resultado final
│   │   └── SistemaLinear.java     // Representação da matriz A e vetor b
│   └── service
│       ├── MetodoGauss.java       // Implementação da Eliminação de Gauss
│       └── MetodoGaussSeidel.java // Implementação do método de Gauss-Seidel
└── resources/br/edu/projeto4
    └── view
        ├── MainView.fxml          // Estrutura da interface gráfica (XML)
        └── style.css              // Estilização da interface
```

---

## 🤝 Equipe de Desenvolvimento

| Nome do Aluno | GitHub |
| :--- | :--- |
| [Rafael de Santana Chaves] | [@RafaelSantana-Dev]([https://github.com/usuario-github-1](https://github.com/RafaelSantana-Dev)) |
| [NOME COMPLETO DO ALUNO 2] | [@usuario-github-2](https://github.com/usuario-github-2) |
| [NOME COMPLETO DO ALUNO 3] | [@usuario-github-3](https://github.com/usuario-github-3) |
| [NOME COMPLETO DO ALUNO 4] | [@usuario-github-4](https://github.com/usuario-github-4) |
| [NOME COMPLETO DO ALUNO 5] | [@usuario-github-5](https://github.com/usuario-github-5) |

---
*Este projeto foi desenvolvido como requisito avaliativo para a disciplina de Matemática Computacional Aplicada.*
