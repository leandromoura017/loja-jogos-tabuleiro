# Loja de Jogos de Tabuleiro

Uma aplicação web completa para gerenciamento de uma loja de jogos de tabuleiro, desenvolvida com Spring Boot, Thymeleaf, PostgreSQL e Docker.

##  Funcionalidades

### Funcionalidades Básicas
- ✅ Listagem de jogos não deletados na página inicial
- ✅ Página de administração com todos os jogos (incluindo deletados)
- ✅ Cadastro e edição de jogos com validações
- ✅ Soft delete e restauração de jogos
- ✅ Carrinho de compras com sessão HTTP
- ✅ Sistema de autenticação e autorização com Spring Security
- ✅ Páginas personalizadas de login e cadastro de usuários
- ✅ Controle de acesso baseado em roles (ADMIN e USER)


### Funcionalidades Avançadas
- ✅ API RESTful com relacionamentos 1-1, 1-N e N-N
- ✅ DTOs para request/response
- ✅ Paginação de resultados
- ✅ Soft delete com logger de aplicação
- ✅ Endpoints CRUD completos para todos os recursos
- ✅ Segurança stateless com JWT (implementação preparada)
- ✅ HATEOAS para navegação de API (implementação preparada)

## ️ Tecnologias Utilizadas

- **Backend**: Java 17, Spring Boot 3.4.1
- **Frontend**: Thymeleaf, Bootstrap 3, HTML5, CSS3, JavaScript
- **Banco de Dados**: PostgreSQL 15
- **Segurança**: Spring Security 6, BCrypt
- **Containerização**: Docker, Docker Compose
- **Build**: Maven
- **Deploy**: Render.com (configurado)

##  Modelo de Dados

### Relacionamentos
- **Usuario (1) ↔ (1) Perfil**: Relacionamento 1-1
- **Categoria (1) ↔ (N) JogoTabuleiro**: Relacionamento 1-N
- **JogoTabuleiro (N) ↔ (N) Tag**: Relacionamento N-N

### Entidades Principais
- `JogoTabuleiro`: Produto principal da loja
- `Usuario`: Usuários do sistema (ADMIN/USER)
- `Categoria`: Categorização dos jogos
- `Tag`: Etiquetas para classificação adicional
- `Perfil`: Informações adicionais do usuário


##  Funcionalidades por Perfil

### Usuário Não Autenticado
- Visualizar jogos na página inicial
- Fazer login/cadastro

### Usuário Comum 
- Todas as funcionalidades não autenticadas
- Adicionar jogos ao carrinho
- Visualizar carrinho
- Finalizar compra

### Administrador 
- Todas as funcionalidades de usuário comum
- Acessar página de administração
- Cadastrar/editar jogos
- Deletar/restaurar jogos
- Gerenciar categorias e tags

## Estrutura do Projeto
```
src/
├── main/
│   ├── java/com/loja/jogostabuleiro/
│   │   ├── config/          # Configurações (Security, etc.)
│   │   ├── controller/      # Controllers REST e Web
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios JPA
│   │   └── service/        # Lógica de negócio
│   └── resources/
│       ├── static/         # CSS, JS, imagens
│       ├── templates/      # Templates Thymeleaf
│       └── application.properties
└── test/                   # Testes unitários
```

## Imagens do Projeto

### Tela Inicial

![Image](https://github.com/user-attachments/assets/658416dd-99de-4cb6-892f-c1739cad468e)

### Tela de Login

<img width="1600" height="712" alt="Image" src="https://github.com/user-attachments/assets/e1070936-ce86-48e5-b518-6de0954735f9" />

### Tela de Cadastro de Usuário

<img width="1600" height="730" alt="Image" src="https://github.com/user-attachments/assets/130126dd-438c-4f03-b159-b4122ee1ce3b" />

### Tela do Carrinho de Compras

<img width="986" height="473" alt="Image" src="https://github.com/user-attachments/assets/c0d97c9a-04bc-4274-af43-12c5b00d8572" />

### Tela do ADMIN 

<img width="502" height="488" alt="Image" src="https://github.com/user-attachments/assets/9645e1eb-0b4c-427d-88e7-76afb9da6aeb" />

### Tela de Cadastro de um Novo Jogo

<img width="470" height="287" alt="Image" src="https://github.com/user-attachments/assets/1af23cc8-c576-47fe-ad7b-2466a6d4e423" />
