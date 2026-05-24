# 🤖 Projeto 4 — Otimização de Sistemas Inteligentes e IA

![Java](https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17-orange?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven&logoColor=white)
![MVC](https://img.shields.io/badge/Arquitetura-MVC-brightgreen?style=for-the-badge)

Aplicação desktop desenvolvida em **Java 21** com **JavaFX 17**, utilizando **Maven**, para a disciplina de **Matemática Computacional Aplicada**.

O projeto implementa métodos numéricos aplicados ao contexto de um **firmware de drone autônomo**, responsável por inspeções em ambientes industriais. A aplicação resolve os cinco desafios propostos no **Projeto 4 — Versão A**, incluindo sistemas lineares, raízes de polinômios, integração numérica, derivação numérica, vetores no espaço e interpolação.

A interface foi construída com JavaFX e organizada em abas, permitindo executar cada parte do projeto separadamente, visualizar tabelas, gráficos, erros, resultados e exportar dados para arquivos CSV.

---

## 📌 Projeto Escolhido

```text
Projeto 4 — Otimização de Sistemas Inteligentes e IA
Versão A
Unidade Curricular: Matemática Computacional Aplicada
```

O cenário envolve um drone autônomo que precisa executar rotinas matemáticas para:

- sincronizar tráfego de dados entre múltiplos núcleos de processamento;
- otimizar um hiperparâmetro de rede neural;
- calcular a carga consumida por um sensor LiDAR;
- identificar a inclinação de um painel por visão computacional;
- estimar altitude durante uma falha de sinal GPS.

---

## 🧠 Métodos Numéricos Implementados

| Parte | Problema | Método |
|---|---|---|
| Parte 1 | Sistema linear 4x4 | Eliminação de Gauss + Gauss-Seidel |
| Parte 2 | Raiz de polinômio | Bissecção |
| Parte 3 | Integração e derivação | Trapézios + Simpson 1/3 + Diferença Central |
| Parte 4 | Vetores 3D | Produto Escalar + Produto Vetorial |
| Parte 5 | Interpolação | Lagrange |

---

## 🖥️ Interface da Aplicação

A aplicação possui uma interface JavaFX dividida em abas:

```text
Parte 1 - Sistema Linear
Parte 2 - Raiz de Polinômio
Parte 3 - Integração e Derivada
Parte 4 - Vetores 3D
Parte 5 - Interpolação
Relatório
```

Cada aba apresenta os dados do problema, botões de execução, tabelas, gráficos e resultados numéricos.

A aplicação também permite:

- executar o método exato de Gauss;
- executar o método iterativo de Gauss-Seidel;
- visualizar histórico de iterações;
- plotar gráficos de erro/convergência;
- calcular raiz por bissecção;
- comparar Trapézios e Simpson 1/3;
- calcular derivada por diferença central;
- calcular vetores, ângulo e área;
- calcular interpolação por Lagrange;
- exportar resultados em CSV;
- gerar e salvar relatório em `.txt`.

---

## 📊 Parte 1 — Sistema Linear

### Sincronização de 4 núcleos de processamento

O sistema modela a distribuição de pacotes por segundo entre quatro núcleos do firmware do drone.

Sistema utilizado:

```text
10x1 - 2x2 - x3        = 150
-2x1 + 12x2 - 2x3 - x4 = 200
-x1 - 2x2 + 15x3 - 3x4 = 100
-x2 - 3x3 + 10x4       = 50
```

Forma matricial:

```text
[ 10  -2  -1   0 ] [ x1 ]   [ 150 ]
[ -2  12  -2  -1 ] [ x2 ] = [ 200 ]
[ -1  -2  15  -3 ] [ x3 ]   [ 100 ]
[  0  -1  -3  10 ] [ x4 ]   [  50 ]
```

### Métodos utilizados

- **Eliminação de Gauss** com pivotamento parcial;
- **Gauss-Seidel** com critério de parada por erro relativo;
- tolerância padrão: `1e-5`.

### Resultado esperado

```text
x1 ≈ 21,01775
x2 ≈ 23,36024
x3 ≈ 13,45719
x4 ≈ 11,37318
```

Interpretação: os núcleos devem processar aproximadamente `21,02`, `23,36`, `13,46` e `11,37` pacotes/s para equilibrar o tráfego do sistema embarcado.

---

## 🧮 Parte 2 — Raiz de Polinômio

### Otimização de hiperparâmetro da rede neural

A função polinomial analisada é:

```text
f(w) = w⁴ - 8w³ + 18w² - 10w - 5
```

Intervalo:

```text
[3, 5]
```

Precisão exigida:

```text
1e-7
```

### Método utilizado

Foi utilizado o **Método da Bissecção**, com validação inicial por troca de sinal:

```text
f(3) = -8
f(5) = 20
```

Como há troca de sinal, existe raiz no intervalo.

### Resultado esperado

```text
w ≈ 4,6279018521
erro final < 1e-7
```

A interface exibe todas as iterações em tabela e também plota o erro ao longo do processo.

---

## ⚡ Parte 3 — Integração e Derivação Numérica

### Consumo energético do sensor LiDAR

A corrente consumida pelo sensor é modelada por:

```text
i(t) = -0,1t⁴ + 2t³ - 8t² + 15t + 50
```

O objetivo é calcular:

```text
Q = ∫₀¹⁰ i(t) dt
```

e também a taxa instantânea da corrente em:

```text
t = 5s
```

### Métodos utilizados

- Regra dos Trapézios;
- Regra de Simpson 1/3;
- Diferença Central para derivada numérica.

### Valores padrão da aplicação

```text
n Trapézios = 100
n Simpson   = 100
h derivada  = 0.001
```

### Resultados esperados

```text
Q exato ≈ 1583,3333 C

Trapézios   ≈ 1583,3667 C
Simpson 1/3 ≈ 1583,3333 C

di/dt em t=5 ≈ 35,000 A/s
```

A aplicação também compara graficamente os erros de Trapézios e Simpson conforme o número de partições aumenta.

---

## 📐 Parte 4 — Vetores 3D

### Inclinação de painel solar por visão computacional

Pontos utilizados:

```text
A = (0, 0, 0)
B = (2, 1, 0.5)
C = (1, 3, 0.2)
```

A aplicação calcula:

- vetor AB;
- vetor AC;
- produto escalar;
- ângulo entre os vetores;
- vetor normal por produto vetorial;
- área do triângulo formado pelos pontos.

### Resultados esperados

```text
AB = (2, 1, 0.5)
AC = (1, 3, 0.2)

AB · AC = 5,1
ângulo ≈ 45,3750°

AB × AC = (-1,3 ; 0,1 ; 5,0)
área ≈ 2,5836 unidades²
```

A interface também exibe uma tabela com os componentes vetoriais e um gráfico de barras comparando os valores.

---

## 🛰️ Parte 5 — Interpolação de Lagrange

### Recuperação de altitude em falha de GPS

Leituras disponíveis:

| Tempo t (s) | Altitude h(t) (m) |
|---:|---:|
| 0 | 100 |
| 2 | 115 |
| 4 | 122 |
| 6 | 118 |

Objetivo:

```text
Estimar h(3)
```

### Método utilizado

Foi aplicada a **Interpolação Polinomial de Lagrange** com os quatro pontos disponíveis.

### Resultado esperado

```text
h(3) ≈ 119,6875 m
```

A aplicação apresenta:

- tabela com cada base `Li(3)`;
- contribuição de cada ponto;
- gráfico com os pontos medidos;
- curva interpolada;
- ponto estimado em `t = 3s`.

---

## 📤 Exportações Disponíveis

A aplicação possui botões para exportar arquivos CSV com os resultados calculados:

```text
exports/sistema-linear-iteracoes.csv
exports/raiz-bissecao-iteracoes.csv
exports/amostras-corrente.csv
exports/convergencia-integracao.csv
exports/vetores-3d.csv
exports/interpolacao-lagrange.csv
```

Também é possível gerar um relatório textual:

```text
relatorio-projeto4-versaoA.txt
```

---

## 🏗️ Arquitetura do Projeto

O projeto segue a organização em camadas, baseada no padrão **MVC**.

```text
projeto4-versaoA/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── br/
        │       └── edu/
        │           └── projeto4/
        │               ├── MainApp.java
        │               ├── controller/
        │               │   └── MainController.java
        │               ├── model/
        │               │   ├── CriterioParada.java
        │               │   ├── IteracaoSistemaLinear.java
        │               │   ├── PontoErroIntegracao.java
        │               │   ├── ResultadoDerivada.java
        │               │   ├── ResultadoIntegral.java
        │               │   ├── ResultadoMetodo.java
        │               │   ├── ResultadoSistemaLinear.java
        │               │   └── SistemaLinear.java
        │               └── service/
        │                   ├── InterpolacaoLagrange.java
        │                   ├── MetodoGauss.java
        │                   ├── MetodoGaussSeidel.java
        │                   ├── MetodoJacobi.java
        │                   ├── SessaoIterativaLinear.java
        │                   └── VerificadorConvergencia.java
        └── resources/
            └── br/
                └── edu/
                    └── projeto4/
                        └── view/
                            └── MainView.fxml
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

O objetivo do projeto é aplicar métodos numéricos estudados na disciplina em um problema realista de engenharia e tecnologia, analisando:

- precisão dos resultados;
- convergência dos métodos;
- eficiência computacional;
- comparação entre métodos diretos e iterativos;
- interpretação prática dos valores obtidos;
- implementação em Java com interface gráfica.

---

## 🤝 Equipe de Desenvolvimento

| Nome do Aluno | GitHub |
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