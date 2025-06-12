# 📚 Literalura: Catálogo de Livros e Autores

Um projeto Spring Boot que interage com a API Gutendex para buscar e gerenciar um catálogo de livros e seus autores, permitindo persistir os dados em um banco de dados relacional.
O projeto é um desafio que faz parte da Formação ONE - Oracle Next Education + Alura Latam.

## ✨ Funcionalidades

O programa oferece um menu interativo com as seguintes opções:

1.  **Buscar livro pelo título:** Pesquisa um livro na API Gutendex pelo título, exibe os detalhes e permite salvá-lo no banco de dados, incluindo seu autor (se ainda não existir).
2.  **Listar todos os livros registrados:** Exibe todos os livros que foram salvos no banco de dados.
3.  **Listar todos os autores registrados:** Exibe todos os autores que foram salvos no banco de dados.
4.  **Listar autores vivos em um determinado ano:** Permite pesquisar autores que estavam vivos em um ano específico, filtrando os resultados do banco de dados.
5.  **Listar livros em um determinado idioma:** Filtra e exibe os livros salvos com base em um código de idioma (ex: `en` para Inglês, `pt` para Português).
6.  **Sair:** Encerra a aplicação.

## 🛠️ Tecnologias Utilizadas

* **Java 17 (ou superior)**
* **Spring Boot 3.x:** Framework para desenvolvimento de aplicações Java.
* **Spring Data JPA:** Para persistência de dados e interação com o banco de dados.
* **Hibernate:** Implementação do JPA.
* **PostgreSQL:** Banco de dados relacional para armazenamento dos livros e autores.
* **API Gutendex:** API pública para buscar informações sobre livros (https://gutendex.com/).
* **Maven:** Gerenciamento de dependências e construção do projeto.

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e rodar a aplicação em sua máquina:

### Pré-requisitos

* JDK 17 (ou superior) instalado.
* Maven instalado.
* Servidor PostgreSQL configurado e rodando.
* Ter um banco de dados chamado `literalura_db` (ou o nome que você definiu no `application.properties`) no PostgreSQL.

