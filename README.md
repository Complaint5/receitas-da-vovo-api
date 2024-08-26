Projeto de API RESTful para Receitas

Visão Geral

Este projeto consiste em uma API RESTful para gerenciar receitas culinárias. A aplicação é desenvolvida em Java com Spring Boot, utiliza Spring Security para autenticação e autorização, gera tokens JWT (JSON Web Tokens) com OAuth2 e persiste os dados em um banco de dados PostgreSQL. A API segue boas práticas de desenvolvimento e mantém o código limpo.

Funcionalidades Principais

Os usuários podem se registrar e fazer login na API o Spring Security é configurado para autenticar os usuários e gerar tokens JWT. Cada usuário possui uma ou mais roles (por exemplo, ROLE_STANDARD, ROLE_ADMIN).

Endpoints

Autenticação

[POST] /v1/authenticate/login: Autentica o usuário e gera um token JWT.

Comentário

[POST] /v1/comment: Registra um novo comentário [PUT] /v1/comment/{id}: Atualiza o comentário [DELETE] /v1/comment/{id}: Atualiza o status de activated do comentário para falso [GET] /v1/comment/{id}: Busca o comentário pelo id [GET] /v1/comment/recipe/{id}: Busca o comentário pelo id da receita

Receita

[POST] /v1/recipe: Registra uma nova receita [PUT] /v1/recipe/{id}: Atualiza a receita [DELETE] /v1/recipe/{id}: Atualiza o status de activated da receita para falso [GET] /v1/recipe/{id}: Busca a receita pelo id [GET] /v1/recipe/user/{id}: Busca as receitas pelo id do usuário [GET] /v1/recipe: Busca todas as receitas

Avaliação

[POST] /v1/recipe/rating: Registra uma nova avaliação [PUT] /v1/recipe/rating/{id}: Atualiza a avaliação [GET] /v1/recipe/rating/{id}: Busca a avaliação pelo id

Usuário

[POST] /v1/user: Registra um novo usuário [PUT] /v1/user/{id}: Atualiza usuário [DELETE] /v1/user/{id}: Atualiza o status de activated do usuário para falso [GET] /v1/user/{id}: Busca o usuário pelo id [GET] /v1/user: Busca todos os usuários

Alguns endpoints não precisam estar autenticados como o endpoint de buscar todas as receitas, já outros precisam estar autenticados e alguns além de estar autenticados precisam ter permissões de administrador para acessá-lo.

Validação de Dados

Os dados enviados para a API são validados com campos obrigatórios (como título da receita, descrição, etc.), formato correto (por exemplo, e-mail válido) e erros de validação que retornam mensagens claras.

Entidades e Relacionamentos

User: Representa um usuário com informações como nome, e-mail e senha. Recipe: Contém detalhes de uma receita com título, descrição. RecipeRating: Contém a avaliação de uma receita. Comment: Armazena os comentários feitos pelos usuários em uma receita.

Os relacionamentos são mapeados usando JPA.

Parâmetros Personalizados

Cada endpoint tem seus próprios parâmetros personalizados. Por exemplo, para criar uma receita, o cliente envia um objeto JSON com os campos necessários e a resposta do endpoint também é mapeada para uma classe record específica.

Tratamento de Exceções

A API trata exceções e retorna um ResponseEntity com o status HTTP correspondente à exceção lançada como BadRequestException, UserNotFoundException, etc.

Boas Práticas e Código Limpo

O código segue convenções de nomenclatura e é organizado em camadas (controladores, serviços, repositórios) e utiliza DTOs (Data Transfer Objects) para representar os dados de entrada e saída.

Próximos Passos

Além disso, estou planejando adicionar funcionalidades como uma nova API para enviar e-mails quando um novo usuário se cadastrar, por exemplo. E com a comunicação entre as APIs usando mensageria com o Kafka e cache utilizando o Redis para melhorar o desempenho.
