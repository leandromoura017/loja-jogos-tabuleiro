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
