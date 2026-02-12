# Desafio Itaú

Este projeto é uma solução para o **Desafio de Programação do Itaú**, cujo objetivo é desenvolver uma **API REST em Java com Spring Boot** para registrar transações financeiras e calcular estatísticas com base nessas transações.

A aplicação armazena dados em memória e expõe endpoints REST conforme as especificações do desafio.

[Link do desafio](https://github.com/feltex/desafio-itau-backend)

---

## 📦 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.4
- Maven (Maven Wrapper)
- JSON  
- Armazenamento em memória (sem banco de dados)

---

## 🚀 Funcionalidades

### 🧾 Registrar Transações

**Endpoint:** `POST /transacao`

Recebe um JSON representando uma transação:

```json
{
    "valor": 123.45,
    "dataHora": "2026-02-11T17:01:00.789-03:00"
}
```

Regras de validação:
O JSON deve conter os campos valor e dataHora.

- O valor não pode ser negativo.

- A dataHora não pode estar no futuro.

- A transação deve estar dentro da janela de tempo válida (ex: últimos 60 segundos, se aplicável à regra do desafio).

Respostas esperadas:
- 201 Created — Transação aceita com sucesso.

- 422 Unprocessable Entity — Dados inválidos (valor negativo, data futura, etc.).

- 400 Bad Request — JSON malformado.

---

### 🗑️ Limpar Transações
Endpoint: DELETE /transacao

Remove todas as transações armazenadas em memória.

Resposta esperada:
- 200 OK — Todas as transações foram removidas com sucesso.

---

### 📊 Estatísticas
Endpoint: GET /estatisticas

Retorna estatísticas das transações registradas dentro da janela de tempo configurada.

Por padrão, considera os últimos 60 segundos.  
É possível definir outro intervalo utilizando o parâmetro `seconds`.

#### Exemplo de requisição
`
GET /estatisticas?seconds=3600 
`

Exemplo de resposta:
```json
{
    "count": 2,
    "sum": 173.45,
    "avg": 86.725,
    "min": 50.0,
    "max": 123.45
}
```
Campos retornados:
- sum — Soma total dos valores.
- avg — Média das transações.
- max — Maior valor registrado.
- min — Menor valor registrado.
- count — Quantidade total de transações consideradas.

Caso não existam transações no período:
```json
{
    "count": 0,
    "sum": 0.0,
    "avg": 0.0,
    "min": 0.0,
    "max": 0.0
}
```

---

# 🛠️ Como Executar o Projeto

## ✅ Pré-requisitos

- Java 21 instalado - [Download Java 21](https://www.oracle.com/br/java/technologies/downloads/#java21)
- Git instalado

> ⚠️ Este projeto é compatível com **Java 21**.  
> Caso possua múltiplas versões do Java instaladas, certifique-se de que o JDK 21 esteja ativo na sessão atual.

```powershell
java --version
```

---

## 1️⃣ Clonar o Repositório
```bash
git clone https://github.com/gzucob/desafio-itau.git
```
```bash
cd desafio-itau
```

## 2️⃣ Configuração temporária do Java (Windows + PowerShell)
Com o projeto aberto no VS Code, IntelliJ ou Eclipse, execute os comandos abaixo no terminal do projeto para garantir que o JDK 21 seja utilizado na sessão atual (lembre-se de conferir se a sua IDE está na versão compatível):

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
```
```powershell
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

## 3️⃣ Após configurar o Java corretamente, execute:

```powershell
.\mvnw clean install
```

## 4️⃣ Executar a Aplicação
```powershell
.\mvnw spring-boot:run
```
A aplicação será iniciada em: 
`
http://localhost:8080
`

