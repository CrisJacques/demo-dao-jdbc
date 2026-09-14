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
2. Crie o banco `coursejdbc` no MySQL, se ainda não existir.
3. Antes de rodar os programas Java, execute o script `populateDatabase.sql` para popular o banco com os dados iniciais necessários aos testes.
4. Abra o projeto no IntelliJ IDEA.
5. Execute as classes:
   - `application.Program` para testar `Seller`;
   - `application.ProgramDepartment` para testar `Department`.

> Importante: o arquivo `populateDatabase.sql` deve ser usado antes de executar `Program` e `ProgramDepartment`, para garantir que as tabelas e registros de exemplo estejam disponíveis.

### SQL mínimo para criar o banco e as tabelas

Caso o script de população ainda não exista no seu ambiente, o trecho abaixo pode ser usado como base para criar a estrutura mínima do projeto:

```sql
CREATE DATABASE coursejdbc;

USE coursejdbc;

CREATE TABLE department (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL
);

CREATE TABLE seller (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(80) NOT NULL,
    Email VARCHAR(80) NOT NULL,
    BirthDate DATE NOT NULL,
    BaseSalary DOUBLE NOT NULL,
    DepartmentId INT NOT NULL,
    CONSTRAINT fk_department_id
        FOREIGN KEY (DepartmentId) REFERENCES department(Id)
);

INSERT INTO department (Name) VALUES
('Computers'),
('Electronics'),
('Fashion'),
('Books');

INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES
('Bob Brown', 'bob@gmail.com', '1998-04-21', 3000.00, 1),
('Maria Green', 'maria@gmail.com', '1979-12-31', 3500.00, 2),
('Alex Grey', 'alex@gmail.com', '1988-01-15', 2200.00, 1),
('Martha Red', 'martha@gmail.com', '1993-11-30', 4200.00, 3);
```

## Observação

Este projeto é didático e foi criado para demonstrar o uso de JDBC com o padrão DAO, sem frameworks ORM, mostrando como a persistência pode ser feita de forma direta e organizada em Java.
