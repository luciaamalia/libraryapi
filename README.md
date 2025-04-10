# 📚 LibraryAPI

API para gerenciamento de livros e autores, desenvolvida com **Spring Boot**, banco **PostgreSQL**, e containerizada com **Docker**.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Validation
- Banco de Dados:
  - PostgreSQL 16.3 (Docker)
  - H2 (ambiente de testes)
- PgAdmin 4 (visualização do banco)
- Docker & Docker Compose

---

## 📂 Estrutura do Projeto

- `controller`: Camada REST com os controllers principais (`LivroController`, `AutorController`) e um handler global de exceções.
- `dto`: Classes de entrada e saída da API (`AutorDTO`, `RequestLivroDTO`, `ErroResponse`, etc.).
- `exceptions`: Exceções customizadas lançadas na aplicação.
- `mappers`: Conversão entre entidades e DTOs.
- `model`: Entidades JPA (`Livro`, `Autor`, `GeneroLivro`).
- `repository`: Repositórios com Spring Data JPA e especificações (`specs`) para consultas dinâmicas.
- `service`: Lógica de negócio central.
- `validator`: Validações específicas de domínio (como `LivroValidator` e `AutorValidator`).

---

## 🐳 Subindo os Containers

Dentro do projeto, há um arquivo com os comandos necessários para subir os containers do PostgreSQL e PgAdmin:

📁 `comandos-docker.txt` 

E também há um arquivo para criar as tabelas, caso necessário.

📁 `comandos-sql.txt` 

---
  🛠️ Funcionalidades
Cadastro e busca de autores.

Cadastro e pesquisa de livros com filtros dinâmicos (via Specifications).

Validações personalizadas.

Tratamento centralizado de exceções.

Mapeamento de DTOs com boas práticas.


