# Auth System

Sistema de autenticação genérico desenvolvido como projeto de prática, com o objetivo de servir como base inicial para outros projetos que necessitem de um sistema de login simples.

## Tecnologias

- Java 21
- Spring Boot 3.4.4
- Spring Security
- Spring Data JPA
- JWT (jjwt 0.12.6)
- H2 Database
- Lombok
- JUnit 5 + Mockito

## Funcionalidades

- Cadastro de usuário
- Login com geração de token JWT
- Proteção de rotas via token
- Tratamento de erros padronizado

## Configuração

Antes de executar, crie o arquivo `src/main/resources/application.properties` com o seguinte conteúdo:

```properties
# Aplicação
spring.application.name=auth-system
server.port=8080

# Banco de Dados
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect

# JWT
jwt.secret=seu_secret_aqui_com_no_minimo_32_caracteres
jwt.access-token-expiration=3600000
```

> **Atenção:** o valor de `jwt.secret` deve ter no mínimo 32 caracteres para o algoritmo HMAC funcionar corretamente.

## Como executar

**Pré-requisitos:** Java 21 e Maven instalados.

```bash
git clone https://github.com/brendobrito2002/auth-system.git
cd auth-system
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## Endpoints

### Cadastro
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senha123"
}
```

### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "password": "senha123"
}
```

### Endpoint protegido (requer token)
```http
GET /api/test/protected
Authorization: Bearer {token}
```

## Estrutura do projeto

```
src/main/java/com/myapp/authsystem/
├── config/security/   # Filtro JWT, configuração do Spring Security
├── controller/        # Endpoints da API
├── dto/               # Objetos de entrada e saída
├── exception/         # Exceções customizadas e handler global
├── model/             # Entidade User e enum Role
├── repository/        # Acesso ao banco de dados
└── service/           # Regras de negócio
```

## Testes

```bash
./mvnw test
```
