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

## 🐳 Como Rodar com Docker

### 1. Subir containers PostgreSQL + PgAdmin

```bash
# PostgreSQL
docker network create library-network

docker run --name librarydb \
  -e POSTGRES_PASSWORD=suasenha \
  -e POSTGRES_USER=seuuser \
  -e POSTGRES_DB=library \
  -p 5432:5432 \
  -d --network library-network \
  postgres:16.3

# PgAdmin
docker run --name pgadmin4 \
  -e PGADMIN_DEFAULT_EMAIL=admin@admin.com \
  -e PGADMIN_DEFAULT_PASSWORD=admin \
  -p 15432:80 \
  -d --network library-network \
  dpage/pgadmin4:8.9
