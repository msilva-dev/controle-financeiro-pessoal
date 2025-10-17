# Sistema de Controle Financeiro Pessoal
[![Cobertura de Testes](https://img.shields.io/codecov/c/github/msilva-dev/controle-financeiro-pessoal?label=Cobertura%20de%20Testes)](https://codecov.io/gh/msilva-dev/controle-financeiro-pessoal)

**Versão Atual:** `0.1.0`
**Status do Projeto:** `Core do Domínio Estável ✅`

[Veja o Histórico de Versões completo no CHANGELOG.md](CHANGELOG.md)

## 1. Visão Geral

Este projeto é uma aplicação de console para gerenciamento de finanças pessoais, desenvolvida como parte do meu portfólio de estudos em Java. O objetivo é construir um sistema robusto, bem testado e com código limpo, aplicando os princípios da Programação Orientada a Objetos (POO) e do Desenvolvimento Orientado a Testes (TDD).

## 2. Arquitetura

O projeto segue os princípios da **Arquitetura Hexagonal (Ports and Adapters)**. O objetivo é isolar completamente o núcleo de regras de negócio (`domain`) das tecnologias de entrega (UI, API) e de infraestrutura (banco de dados), garantindo alta testabilidade e flexibilidade.

## 3. Modelo de Domínio

Nosso domínio é modelado seguindo os princípios da POO, com foco em encapsulamento, herança e baixo acoplamento. Para facilitar a compreensão, a arquitetura do domínio é apresentada em diagramas focados, cada um contando uma parte específica da história do design.

### Diagrama 1: A Arquitetura de `Conta`

Este diagrama ilustra como usamos Interfaces, Classes Abstratas e Herança para criar um modelo de contas flexível, reutilizável e especializado.

![Diagrama da Arquitetura de Contas](./docs/images/arquitetura-contas.jpg)

### Diagrama 2: Relacionamentos do Domínio Principal

Este diagrama mostra a visão macro de como as entidades centrais do sistema (`Usuario`, `Conta`, `Transacao`) se conectam através de associações.

![Diagrama de Relacionamentos do Domínio](./docs/images/relacionamentos-dominio.jpg)

## 4. Tecnologias e Ferramentas

* **Linguagem:** Java 21
* **Gerenciador de Build:** Apache Maven
* **Testes:** JUnit 5, AssertJ
* **Qualidade de Código:** JaCoCo (para cobertura de testes), Codecov (para relatório de cobertura)
* **CI/CD:** GitHub Actions

## 5. Principais Features

### ✅ Implementadas (v0.1.0)
- [x] Modelagem e implementação do Core de Domínio com entidades ricas (`Usuario`, `Conta`, `Transacao`).
- [x] Arquitetura de `Conta` flexível com Interface, Classe Abstrata e implementações (`ContaBancaria`, `CarteiraFisica`).
- [x] Suíte de testes unitários completa para toda a camada de domínio, com ~99% de cobertura.
- [x] Implementação de Padrões de Design (Builder, Factory Method, Command Object).
- [x] Validação de dados robusta em todas as entidades.
- [x] Workflow de Integração Contínua (CI) com validação automática de testes e cobertura.

### 🚧 Planejadas (Próximos Passos)
- [ ] Implementação da interface de linha de comando (CLI) para interação com o usuário.
- [ ] Implementação da camada de persistência com JPA/Hibernate.
- [ ] Exposição do sistema via API REST com Spring Boot.

## 6. Como Executar o Projeto

**Pré-requisitos:**
* Java (JDK) 21 ou superior
* Apache Maven 3.8 ou superior

1.  **Clone o repositório:**
    ```sh
    git clone [https://github.com/msilva-dev/controle-financeiro-pessoal.git](https://github.com/msilva-dev/controle-financeiro-pessoal.git)
    ```
2.  **Navegue até a pasta do projeto:**
    ```sh
    cd controle-financeiro-pessoal
    ```
3.  **Compile e empacote o projeto:**
    ```sh
    mvn clean package
    ```
4.  **Execute a aplicação:**
    ```sh
    java -cp target/controle-financeiro-pessoal-1.0-SNAPSHOT.jar br.com.mateussilva.financeiro.application.Main
    ```

## 7. Como Rodar os Testes e Gerar Relatórios

Para garantir a qualidade e a integridade do código, execute a suíte de testes unitários:
```sh
mvn test