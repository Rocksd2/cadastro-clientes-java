# 👤 Sistema de Cadastro de Clientes e Histórico de Compras

![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)
![Status](https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen)
![Licença](https://img.shields.io/badge/licen%C3%A7a-MIT-blue)

Um sistema de gerenciamento de clientes via linha de comando (CLI) desenvolvido em **Java 17**, aplicando conceitos fundamentais de **Programação Orientada a Objetos (POO)** e a **Java Collections Framework**.

---

## 📌 Funcionalidades

- [x] **Cadastro de Clientes**: Inclusão de nome, e-mail e telefone com ID autoincremental.
- [x] **Listagem Completa**: Visualização de todos os clientes, quantidade de pedidos e valor total gasto.
- [x] **Busca Flexível**: Localização de clientes por ID único ou por parte do nome.
- [x] **Atualização Cadastral**: Edição de dados existentes mantendo valores inalterados via tecla ENTER.
- [x] **Histórico de Compras**: Registro de compras com data/hora automática e descrição do item/serviço.
- [x] **Extrato de Compras**: Detalhamento de todas as compras de um cliente com cálculo do total acumulado.
- [x] **Remoção Segura**: Exclusão de clientes com confirmação prévia.
- [x] **Carga de Dados de Teste**: Botão rápido para popular a aplicação com dados de demonstração.

---

## 🏗️ Estrutura do Projeto

```text
cadastro-clientes/
├── .gitignore
├── README.md
├── bin/                       # Arquivos compilados (.class)
└── src/
    ├── Main.java              # Ponto de entrada e interface CLI interativa
    ├── model/
    │   ├── Cliente.java       # Entidade Cliente (dados e encapsulamento)
    │   └── Compra.java        # Entidade Compra com timestamp
    └── service/
        └── GerenciadorClientes.java # Lógica de negócio e operações de CRUD
```

---

## 🚀 Como Executar

### Pré-requisitos
- **Java JDK 17** ou superior instalado no seu sistema.

### 1. Clonar ou Acessar a Pasta
```bash
cd cadastro-clientes
```

### 2. Compilar os Arquivos
```bash
javac -d bin -sourcepath src src/Main.java
```

### 3. Executar o Programa
```bash
java -cp bin Main
```

---

## 🛠️ Tecnologias e Boas Práticas

- **Java SE 17**
- **Encapsulamento e POO**: Separação clara entre Modelo, Serviço e Visão (CLI).
- **Java Streams & Lambdas**: Utilizado para buscas e filtros eficientes.
- **Collections API**: Uso de `ArrayList` e proteção de coleções com `Collections.unmodifiableList`.
- **Tratamento de Entradas**: Leitura robusta com `Scanner`, evitando travamentos por entradas inválidas ou quebras de linha pendentes.


---
## 📄 Licença
Distribuído sob a licença MIT. Sinta-se livre para utilizar e aprimorar para fins acadêmicos ou profissionais!
