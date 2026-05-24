<div align="center">

# 🤖 Projeto 4 — Otimização de Sistemas Inteligentes e IA

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/JavaFX-17.0.6-orange?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-3.8.1-red?style=for-the-badge&logo=apachemaven&logoColor=white"/>
  <img src="https://img.shields.io/badge/Arquitetura-MVC-brightgreen?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Status-Concluído-success?style=for-the-badge"/>
</p>

<p align="center">
  <strong>Aplicação desktop em Java 21 + JavaFX 17 com métodos numéricos aplicados a um firmware de drone autônomo</strong>
</p>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre</a> •
  <a href="#-métodos-implementados">Métodos</a> •
  <a href="#-interface">Interface</a> •
  <a href="#-resultados">Resultados</a> •
  <a href="#-arquitetura">Arquitetura</a> •
  <a href="#-como-executar">Como Executar</a> •
  <a href="#-equipe">Equipe</a>
</p>

</div>

---

## 📌 Sobre o Projeto

```text
Projeto 4 — Otimização de Sistemas Inteligentes e IA
Versão A
Unidade Curricular: Matemática Computacional Aplicada
Avaliação: A3
```

Esta aplicação simula o **firmware matemático de um drone autônomo** de inspeção industrial. Cada parte do projeto resolve um desafio específico:

| # | Missão | Problema Matemático |
|:-:|--------|---------------------|
| 1 | Sincronizar tráfego de dados entre múltiplos núcleos de processamento | Sistema Linear 4×4 |
| 2 | Otimizar um hiperparâmetro de rede neural embarcada | Raiz de Polinômio |
| 3 | Calcular a carga consumida pelo sensor LiDAR | Integração + Derivação Numérica |
| 4 | Identificar a inclinação de um painel por visão computacional | Vetores no Espaço 3D |
| 5 | Estimar altitude durante falha de sinal GPS | Interpolação Polinomial |

---

## 🧠 Métodos Numéricos Implementados

| Parte | Problema | Método(s) Utilizados |
|:-----:|----------|----------------------|
| **Parte 1** | Sistema linear 4×4 | Eliminação de Gauss com pivotamento + Gauss-Seidel iterativo |
| **Parte 2** | Raiz de Polinômio | Método da Bissecção |
| **Parte 3** | Integração e Derivação | Regra dos Trapézios + Simpson 1/3 + Diferença Central |
| **Parte 4** | Vetores 3D | Produto Escalar + Produto Vetorial |
| **Parte 5** | Interpolação | Lagrange |

---

## 🖥️ Interface da Aplicação

A interface foi construída em **JavaFX + FXML**, organizada em **seis abas** independentes:

```
┌─────────────────────────────────────────────────────────┐
│  Parte 1      Parte 2      Parte 3      Parte 4      Parte 5      Relatório  │
│  Sistema     Raiz de     Integração   Vetores 3D   Interpolação             │
│  Linear      Polinômio   e Derivada                                         │
└─────────────────────────────────────────────────────────┘
```

### Funcionalidades por Aba

**Parte 1 — Sistema Linear**
- Execução do Método Exato (Gauss) com resultado imediato
- Execução do Método Iterativo (Gauss-Seidel) com histórico de iterações
- Visualização da convergência por gráfico de erro relativo
- Tabela com cada iteração e o erro correspondente

**Parte 2 — Raiz de Polinômio**
- Visualização da função f(w) no intervalo definido
- Tabela completa com todas as iterações da Bissecção
- Gráfico do erro ao longo das iterações

**Parte 3 — Integração e Derivada**
- Comparação visual entre Trapézios e Simpson 1/3
- Gráfico de convergência de erro conforme `n` aumenta
- Cálculo da derivada numérica por Diferença Central
- Ajuste dos parâmetros `n` e `h` diretamente na interface

**Parte 4 — Vetores 3D**
- Tabela com os componentes vetoriais AB e AC
- Gráfico de barras comparando os valores dos componentes
- Exibição de produto escalar, ângulo e área do triângulo

**Parte 5 — Interpolação**
- Tabela com as bases L₀, L₁, L₂, L₃ calculadas em t = 3
- Contribuição ponderada de cada ponto
- Gráfico com pontos medidos, curva interpolada e ponto estimado

**Relatório**
- Síntese textual automática de todos os resultados
- Exportação para arquivo `.txt`

---

## 📊 Parte 1 — Sistema Linear

### Sincronização de 4 Núcleos de Processamento

O sistema modela a **distribuição de pacotes por segundo** entre quatro núcleos do firmware do drone.

**Equações:**

```
10x₁ - 2x₂ - x₃            = 150
-2x₁ + 12x₂ - 2x₃ - x₄    = 200
 -x₁ - 2x₂ + 15x₃ - 3x₄   = 100
        -x₂ - 3x₃ + 10x₄  =  50
```

**Forma Matricial `[A|b]`:**

```
⎡ 10  -2  -1   0 ⎤ ⎡ x₁ ⎤   ⎡ 150 ⎤
⎢ -2  12  -2  -1 ⎥ ⎢ x₂ ⎥ = ⎢ 200 ⎥
⎢ -1  -2  15  -3 ⎥ ⎢ x₃ ⎥   ⎢ 100 ⎥
⎣  0  -1  -3  10 ⎦ ⎣ x₄ ⎦   ⎣  50 ⎦
```

> 💡 A matriz é **diagonalmente dominante**, garantindo convergência do Gauss-Seidel.

### Métodos Utilizados

- **Eliminação de Gauss** com pivotamento parcial;
- **Gauss-Seidel** com critério de parada por erro relativo;
- tolerância padrão: `1e-5`.

### Resultado Esperado

```
x₁ ≈ 21,01775   →  Núcleo 1: ~21,02 pacotes/s
x₂ ≈ 23,36024   →  Núcleo 2: ~23,36 pacotes/s
x₃ ≈ 13,45719   →  Núcleo 3: ~13,46 pacotes/s
x₄ ≈ 11,37318   →  Núcleo 4: ~11,37 pacotes/s
```

---

## 🧮 Parte 2 — Raiz de Polinômio

### Otimização de Hiperparâmetro da Rede Neural

A função polinomial analisada é:

```
f(w) = w⁴ - 8w³ + 18w² - 10w - 5
```

**Intervalo:** `[3, 5]`

**Precisão exigida:** `1e-7`

### Método Utilizado

Foi utilizado o **Método da Bissecção**, com validação inicial por troca de sinal:

```
f(3) = -8
f(5) = 20
```

Como há troca de sinal, existe raiz no intervalo.

### Resultado Esperado

```
w ≈ 4,6279018521
erro final < 1e-7
```

---

## ⚡ Parte 3 — Integração e Derivação Numérica

### Consumo Energético do Sensor LiDAR

A corrente consumida pelo sensor é modelada por:

```
i(t) = -0,1t⁴ + 2t³ - 8t² + 15t + 50
```

**Objetivo:** Calcular `Q = ∫₀¹⁰ i(t) dt` e a taxa instantânea em `t = 5s`.

### Métodos Utilizados

- Regra dos Trapézios;
- Regra de Simpson 1/3;
- Diferença Central para derivada numérica.

### Valores Padrão da Aplicação

```
n (Trapézios) = 100
n (Simpson)   = 100
h (derivada) = 0.001
```

### Resultados Esperados

```
Q exato ≈ 1583,3333 C
Trapézios   ≈ 1583,3667 C
Simpson 1/3 ≈ 1583,3333 C

di/dt em t=5 ≈ 35,000 A/s
```

A aplicação também compara graficamente os erros de Trapézios e Simpson conforme o número de partições aumenta.

---

## 📐 Parte 4 — Vetores 3D

### Inclinação de Painel Solar por Visão Computacional

**Pontos Utilizados:**
```
A = (0, 0, 0)
B = (2, 1, 0.5)
C = (1, 3, 0.2)
```

**Cálculos Realizados:**
- Vetor AB = (2, 1, 0.5)
- Vetor AC = (1, 3, 0.2)
- Produto escalar AB·AC = 5,1
- Ângulo entre os vetores ≈ 45,3750°
- Produto vetorial AB×AC = (-1,3 ; 0,1 ; 5,0)
- Área do triângulo = 2,5836 unidades²

---

## 🛰️ Parte 5 — Interpolação de Lagrange

### Recuperação de Altitude em Falha de GPS

**Leituras Disponíveis:**
| Tempo t (s) | Altitude h(t) (m) |
|---:|---:|
| 0 | 100 |
| 2 | 115 |
| 4 | 122 |
| 6 | 118 |

**Objetivo:** Estimar h(3).

**Resultado Esperado:** `h(3) ≈ 119,6875 m`

---

## 📤 Exportações Disponíveis

A aplicação possui botões para exportar arquivos CSV com os resultados calculados:
```
exports/sistema-linear-iteracoes.csv
exports/raiz-bissecao-iteracoes.csv
exports/amostras-corrente.csv
exports/convergencia-integracao.csv
exports/vetores-3d.csv
exports/interpolacao-lagrange.csv
```

Também é possível gerar um relatório textual:
```
relatorio-projeto4-versaoA.txt
```

---

## 🏗️ Arquitetura do Projeto

O projeto segue a organização em camadas, baseada no padrão **MVC**.
```
projeto4-versaoA/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── br/edu/projeto4/
        │       ├── MainApp.java
        │       ├── controller/MainController.java
        │       ├── model/…
        │       └── service/…
        └── resources/…
```

---

## 🧩 Principais Classes

| Classe | Função |
|---|---|
| `MainApp` | Classe principal que inicia a aplicação JavaFX |
| `MainController` | Controla a interface, eventos, cálculos e exportações |
| `SistemaLinear` | Representa a matriz A e o vetor b |
| `ResultadoMetodo` | Armazena solução, erro, iterações, tempo e status |
| `IteracaoSistemaLinear` | Representa uma linha da tabela de iterações |
| `MetodoGauss` | Implementa a Eliminação de Gauss |
| `MetodoGaussSeidel` | Implementa o método iterativo de Gauss-Seidel |
| `MetodoJacobi` | Implementa o método de Jacobi |
| `InterpolacaoLagrange` | Implementa a interpolação polinomial de Lagrange |
| `VerificadorConvergencia` | Verifica se a matriz é diagonal dominante |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão / Uso |
|---|---|
| Java | 21 |
| JavaFX Controls | 17.0.6 |
| JavaFX FXML | 17.0.6 |
| Maven Compiler Plugin | 3.8.1 |
| JavaFX Maven Plugin | 0.0.8 |
| Arquitetura | MVC |
| Interface | FXML |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 21 ou superior;
- Apache Maven instalado;
- Git instalado.

### Clonar o repositório
```bash
git clone https://github.com/RafaelSantana-Dev/projeto-calculo-numerico.git
```

### Acessar a pasta do projeto
```bash
cd projeto-calculo-numerico/projeto4-versaoA
```

### Executar com Maven
```bash
mvn clean javafx:run
```

Se estiver fora da pasta do projeto, também é possível executar apontando para o `pom.xml`:
```bash
mvn -f caminho/para/projeto4-versaoA/pom.xml clean javafx:run
```

---

## 🧪 Como Usar

Ao abrir a aplicação:
1. A aba **Parte 1 - Sistema Linear** carrega o sistema do Projeto 4A.
2. Clique em **Método Exato (Gauss)** para resolver diretamente.
3. Clique em **Método Iterativo** para executar Gauss-Seidel e visualizar as iterações.
4. Acesse a aba **Parte 2** para visualizar a bissecção.
5. Acesse a aba **Parte 3** para comparar Trapézios, Simpson 1/3 e derivada central.
6. Acesse a aba **Parte 4** para visualizar os cálculos vetoriais.
7. Acesse a aba **Parte 5** para ver a interpolação de Lagrange.
8. Use a aba **Relatório** para gerar uma síntese textual dos resultados.

---

## 📈 Resultados Gerais

| Parte | Método | Resultado principal |
|---|---|---|
| Parte 1 | Gauss / Gauss-Seidel | x ≈ (21,01775; 23,36024; 13,45719; 11,37318) |
| Parte 2 | Bissecção | w ≈ 4,6279018521 |
| Parte 3 | Simpson 1/3 | Q ≈ 1583,3333 C |
| Parte 3 | Diferença Central | di/dt ≈ 35,000 A/s |
| Parte 4 | Produto escalar/vetorial | θ ≈ 45,3750° e área ≈ 2,5836 |
| Parte 5 | Lagrange | h(3) ≈ 119,6875 m |

---

## 🎯 Objetivo Acadêmico

O objetivo do projeto é aplicar métodos numéricos estudados na disciplina em um problema realista de engenharia, analisando:
- precisão dos resultados;
- convergência dos métodos;
- eficiência computacional;
- comparação entre métodos diretos e iterativos;
- interpretação prática dos valores obtidos;
- implementação em Java com interface gráfica.

---

## 🤝 Equipe de Desenvolvimento

| Nome | GitHub |
|---|---|
| Cesar Augusto Jorge Cantoia | [@cesarcantoia27](https://github.com/cesarcantoia27) |
| Felipe Ventura Cassiolato | [@Felpz0kkj](https://github.com/Felpz0kkj) |
| Kauan Dos Santos Silva | [@KssCyber](https://github.com/KssCyber) |
| Pedro Argeri da Silva | [@ArgeriPedro](https://github.com/ArgeriPedro) |
| Rafael de Santana Chaves | [@RafaelSantana-Dev](https://github.com/RafaelSantana-Dev) |

---

## 📚 Disciplina

**Matemática Computacional Aplicada**

Projeto desenvolvido como requisito avaliativo da A3, com foco na aplicação prática de métodos numéricos em Java.