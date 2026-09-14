# Demo DAO JDBC

Projeto Java demonstrativo de acesso a banco de dados com JDBC, seguindo o padrão DAO (Data Access Object).

## Objetivo

Este projeto tem como finalidade mostrar na prática como:

- conectar uma aplicação Java a um banco MySQL;
- separar a lógica de acesso a dados da lógica de negócio;
- modelar entidades Java e persistir seus dados no banco;
- implementar operações CRUD com JDBC;
- organizar a estrutura do projeto em camadas.

## Tecnologias utilizadas

- Java
- JDBC
- MySQL
- IntelliJ IDEA
- Padrão DAO

## Estrutura do projeto

```text
demo-dao-jdbc/
├── db.properties
├── db.properties.example
├── src/
│   ├── application/
│   │   ├── Program.java
│   │   └── ProgramDepartment.java
│   ├── db/
│   │   ├── DB.java
│   │   ├── DBException.java
│   │   └── DbIntegrityException.java
│   └── model/
│       ├── dao/
│       │   ├── DaoFactory.java
│       │   ├── DepartmentDao.java
│       │   ├── SellerDao.java
│       │   └── impl/
│       │       ├── DepartmentDaoJDBC.java
│       │       └── SellerDaoJDBC.java
│       └── entities/
│           ├── Department.java
│           └── Seller.java
└── README.md
```

## Entidades principais

- `Department`: representa um departamento.
- `Seller`: representa um vendedor, com relacionamento com `Department`.

## Camadas da aplicação

### 1. Entities
As classes em `src/model/entities` representam os objetos do domínio da aplicação.

### 2. DAO
As interfaces em `src/model/dao` definem as operações de persistência, como:

- insert
- update
- deleteById
- findById
- findAll
- findByDepartment

As implementações JDBC ficam em `src/model/dao/impl`.

### 3. Factory
A classe `DaoFactory` centraliza a criação dos DAOs e evita acoplamento direto com a conexão.

### 4. DB
A classe `DB` cuida da conexão com o banco e da execução de operações comuns de JDBC, como fechamento de `Statement` e `ResultSet`.

## Configuração do banco

O projeto usa um arquivo `db.properties` para armazenar a configuração da conexão com o MySQL.

Exemplo:

```properties
user=root
password=********
dburl=jdbc:mysql://localhost:3306/coursejdbc
useSSL=false
```

Há também um arquivo de exemplo em `db.properties.example`.

## Funcionalidades demonstradas

- buscar vendedor por ID;
- listar todos os vendedores;
- buscar vendedores por departamento;
- inserir vendedor;
- atualizar vendedor;
- excluir vendedor;
- inserir, atualizar e excluir departamento;
- consultar dados relacionando `seller` e `department`.

## Como executar

1. Configure o arquivo `db.properties` com as credenciais do seu MySQL.
2. Certifique-se de que o banco `coursejdbc` exista e tenha as tabelas necessárias.
3. Abra o projeto no IntelliJ IDEA.
4. Execute as classes:
   - `application.Program` para testar `Seller`;
   - `application.ProgramDepartment` para testar `Department`.

## Observação

Este projeto é didático e foi criado para demonstrar o uso de JDBC com o padrão DAO, sem frameworks ORM, mostrando como a persistência pode ser feita de forma direta e organizada em Java.
