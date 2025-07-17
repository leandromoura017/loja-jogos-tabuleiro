# Guia Completo de Execução - Loja de Jogos de Tabuleiro

## Índice
1. [Pré-requisitos e Configuração do Ambiente](#pré-requisitos-e-configuração-do-ambiente)
2. [Instalação e Configuração do IntelliJ IDEA Ultimate](#instalação-e-configuração-do-intellij-idea-ultimate)
3. [Instalação e Configuração do Docker](#instalação-e-configuração-do-docker)
4. [Execução do Projeto](#execução-do-projeto)
5. [Verificação e Testes](#verificação-e-testes)
6. [Solução de Problemas](#solução-de-problemas)
7. [Deploy em Produção](#deploy-em-produção)

---

## Pré-requisitos e Configuração do Ambiente

### Requisitos de Sistema

Para executar este projeto com sucesso, seu computador deve atender aos seguintes requisitos mínimos:

**Sistema Operacional:**
- Windows 10/11 (64-bit)
- macOS 10.14 ou superior
- Linux (Ubuntu 18.04+, CentOS 7+, ou distribuições equivalentes)

**Hardware:**
- Processador: Intel Core i5 ou AMD Ryzen 5 (ou superior)
- Memória RAM: 8 GB (recomendado 16 GB)
- Espaço em disco: 10 GB livres
- Conexão com internet estável

### Instalação do Java 17

O projeto utiliza Java 17, que é uma versão LTS (Long Term Support) do OpenJDK. Siga os passos abaixo para instalar:

**Windows:**
1. Acesse o site oficial do OpenJDK: https://adoptium.net/
2. Baixe o OpenJDK 17 para Windows x64
3. Execute o instalador e siga as instruções
4. Abra o Prompt de Comando e execute: `java -version`
5. Verifique se a versão exibida é 17.x.x

**macOS:**
1. Instale o Homebrew se não tiver: `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
2. Execute: `brew install openjdk@17`
3. Configure o PATH: `echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc`
4. Recarregue o terminal: `source ~/.zshrc`
5. Verifique: `java -version`

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
java -version
```

### Configuração das Variáveis de Ambiente

Após instalar o Java, configure as variáveis de ambiente:

**Windows:**
1. Abra "Configurações do Sistema" → "Configurações Avançadas do Sistema"
2. Clique em "Variáveis de Ambiente"
3. Adicione uma nova variável de sistema:
   - Nome: `JAVA_HOME`
   - Valor: Caminho da instalação do Java (ex: `C:\Program Files\Eclipse Adoptium\jdk-17.0.x-hotspot`)
4. Edite a variável `PATH` e adicione: `%JAVA_HOME%\bin`

**macOS/Linux:**
Adicione ao arquivo `~/.bashrc` ou `~/.zshrc`:
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64  # Linux
export JAVA_HOME=/opt/homebrew/opt/openjdk@17         # macOS
export PATH=$JAVA_HOME/bin:$PATH
```

---

## Instalação e Configuração do IntelliJ IDEA Ultimate

### Download e Instalação

1. **Acesse o site oficial:** https://www.jetbrains.com/idea/
2. **Baixe a versão Ultimate** (necessária para suporte completo ao Spring Boot)
3. **Execute o instalador** seguindo as instruções do seu sistema operacional
4. **Ative a licença** (estudantes podem obter licença gratuita em https://www.jetbrains.com/student/)

### Configuração Inicial

Após a instalação, configure o IntelliJ IDEA:

1. **Abra o IntelliJ IDEA**
2. **Configure o JDK:**
   - File → Project Structure → Project Settings → Project
   - Project SDK: Selecione Java 17
   - Project language level: 17

3. **Instale plugins necessários:**
   - File → Settings → Plugins
   - Verifique se estão instalados:
     - Spring Boot
     - Thymeleaf
     - Database Tools and SQL
     - Docker
     - Lombok

4. **Configure o Maven:**
   - File → Settings → Build, Execution, Deployment → Build Tools → Maven
   - Maven home directory: Use bundled (Maven 3)
   - User settings file: Use default

### Importação do Projeto

1. **Extraia o arquivo ZIP** do projeto em uma pasta de sua escolha
2. **Abra o IntelliJ IDEA**
3. **Importe o projeto:**
   - File → Open
   - Navegue até a pasta extraída
   - Selecione o arquivo `pom.xml`
   - Clique em "Open as Project"
4. **Aguarde o Maven** baixar todas as dependências (pode levar alguns minutos)
5. **Verifique se não há erros** na aba "Problems" na parte inferior

---

## Instalação e Configuração do Docker

### Instalação do Docker Desktop

**Windows:**
1. Acesse: https://www.docker.com/products/docker-desktop
2. Baixe o Docker Desktop para Windows
3. Execute o instalador
4. Reinicie o computador quando solicitado
5. Abra o Docker Desktop e aguarde a inicialização
6. Verifique no terminal: `docker --version`

**macOS:**
1. Baixe o Docker Desktop para Mac
2. Arraste para a pasta Applications
3. Execute o Docker Desktop
4. Siga as instruções de configuração
5. Verifique: `docker --version`

**Linux (Ubuntu):**
```bash
# Remover versões antigas
sudo apt-get remove docker docker-engine docker.io containerd runc

# Instalar dependências
sudo apt-get update
sudo apt-get install apt-transport-https ca-certificates curl gnupg lsb-release

# Adicionar chave GPG oficial do Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# Adicionar repositório
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Instalar Docker
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io

# Instalar Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/v2.20.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Adicionar usuário ao grupo docker
sudo usermod -aG docker $USER

# Reiniciar sessão ou executar:
newgrp docker

# Verificar instalação
docker --version
docker-compose --version
```

### Configuração do Docker

1. **Abra o Docker Desktop**
2. **Configure recursos:**
   - Settings → Resources
   - Memory: Pelo menos 4 GB
   - CPUs: Pelo menos 2 cores
3. **Habilite Kubernetes** (opcional):
   - Settings → Kubernetes → Enable Kubernetes

---

## Execução do Projeto

### Método 1: Execução com Docker (Recomendado)

Este é o método mais simples e confiável para executar o projeto:

1. **Abra o terminal** na pasta do projeto
2. **Verifique se o Docker está rodando:**
   ```bash
   docker --version
   docker-compose --version
   ```

3. **Execute o projeto:**
   ```bash
   docker-compose up -d
   ```

4. **Aguarde o download das imagens** (primeira execução pode demorar)
5. **Verifique os containers:**
   ```bash
   docker-compose ps
   ```

6. **Acesse a aplicação:**
   - URL: http://localhost:8080
   - Usuário Admin: `admin` / `admin123`
   - Usuário Comum: `user` / `user123`

### Método 2: Execução no IntelliJ IDEA

Para desenvolvimento e debug, execute diretamente no IntelliJ:

1. **Inicie o banco PostgreSQL:**
   ```bash
   docker run --name postgres-loja -e POSTGRES_DB=loja_jogos_tabuleiro -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:15-alpine
   ```

2. **No IntelliJ IDEA:**
   - Localize a classe `JogosTabuleiroApplication.java`
   - Clique com botão direito → "Run 'JogosTabuleiroApplication'"
   - Ou use o atalho: Ctrl+Shift+F10 (Windows/Linux) ou Cmd+Shift+R (macOS)

3. **Aguarde a inicialização** (verifique o console)
4. **Acesse:** http://localhost:8080

### Método 3: Execução via Maven (Terminal)

Para execução via linha de comando:

1. **Inicie o PostgreSQL** (mesmo comando do Método 2)
2. **No terminal do projeto:**
   ```bash
   # Windows
   mvnw.cmd spring-boot:run
   
   # macOS/Linux
   ./mvnw spring-boot:run
   ```

3. **Aguarde a mensagem:** "Started JogosTabuleiroApplication"
4. **Acesse:** http://localhost:8080

---

## Verificação e Testes

### Verificação da Aplicação

Após iniciar a aplicação, realize os seguintes testes:

1. **Teste de Conectividade:**
   - Acesse: http://localhost:8080
   - Deve exibir a página inicial com jogos

2. **Teste de Login:**
   - Clique em "Login" no canto superior direito
   - Use: `admin` / `admin123`
   - Deve redirecionar para a página inicial logado

3. **Teste de Administração:**
   - Com usuário admin logado
   - Acesse: http://localhost:8080/admin
   - Deve exibir a página de administração

4. **Teste de Cadastro:**
   - Na página admin, clique "Cadastrar Novo Jogo"
   - Preencha os campos obrigatórios
   - Clique "Cadastrar"
   - Deve salvar e redirecionar

5. **Teste do Carrinho:**
   - Faça logout e login como `user` / `user123`
   - Na página inicial, clique "Adicionar ao Carrinho"
   - Clique no ícone do carrinho
   - Deve exibir os itens adicionados

### Verificação do Banco de Dados

Para verificar se o banco está funcionando:

1. **Via Docker:**
   ```bash
   docker exec -it loja-jogos-postgres psql -U postgres -d loja_jogos_tabuleiro
   ```

2. **Comandos SQL úteis:**
   ```sql
   -- Listar tabelas
   \dt
   
   -- Ver usuários
   SELECT * FROM usuarios;
   
   -- Ver jogos
   SELECT * FROM jogos_tabuleiro;
   
   -- Sair
   \q
   ```

3. **Via IntelliJ IDEA:**
   - View → Tool Windows → Database
   - Adicione nova conexão PostgreSQL
   - Host: localhost, Port: 5432
   - Database: loja_jogos_tabuleiro
   - User: postgres, Password: postgres

### Logs e Monitoramento

Para monitorar a aplicação:

1. **Logs do Docker:**
   ```bash
   # Ver logs da aplicação
   docker-compose logs app
   
   # Ver logs do banco
   docker-compose logs postgres
   
   # Seguir logs em tempo real
   docker-compose logs -f app
   ```

2. **Logs no IntelliJ:**
   - Verifique o console durante a execução
   - Procure por mensagens de erro em vermelho

3. **Arquivos de log:**
   - Logs são exibidos no console
   - Para produção, configure logging em arquivo

---

## Solução de Problemas

### Problemas Comuns e Soluções

**Erro: "Port 8080 is already in use"**
```bash
# Encontrar processo usando a porta
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # macOS/Linux

# Parar containers Docker
docker-compose down

# Ou mudar a porta no application.properties
server.port=8081
```

**Erro: "Could not connect to database"**
```bash
# Verificar se PostgreSQL está rodando
docker ps

# Reiniciar banco de dados
docker-compose restart postgres

# Verificar logs do banco
docker-compose logs postgres
```

**Erro: "Java version not supported"**
- Verifique se Java 17 está instalado: `java -version`
- Configure JAVA_HOME corretamente
- No IntelliJ: File → Project Structure → Project SDK

**Erro: "Maven dependencies not found"**
```bash
# Limpar cache do Maven
./mvnw clean

# Redownload dependencies
./mvnw dependency:resolve

# No IntelliJ: Maven → Reload Projects
```

**Erro: "Docker not found"**
- Verifique se Docker Desktop está rodando
- Reinicie o Docker Desktop
- Verifique: `docker --version`

**Erro: "Permission denied" (Linux/macOS)**
```bash
# Dar permissão ao Maven wrapper
chmod +x mvnw

# Dar permissão ao script de build
chmod +x build.sh
```

### Debug Avançado

Para problemas mais complexos:

1. **Habilitar debug no IntelliJ:**
   - Run → Debug 'JogosTabuleiroApplication'
   - Adicione breakpoints no código
   - Examine variáveis e stack trace

2. **Logs detalhados:**
   - Edite `application.properties`:
   ```properties
   logging.level.com.loja.jogostabuleiro=DEBUG
   logging.level.org.springframework=DEBUG
   ```

3. **Profile de desenvolvimento:**
   - Crie `application-dev.properties`
   - Configure logs mais verbosos
   - Use: `spring.profiles.active=dev`

---

## Deploy em Produção

### Preparação para Deploy no Render.com

1. **Crie um repositório no GitHub:**
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/seu-usuario/loja-jogos-tabuleiro.git
   git push -u origin main
   ```

2. **Configure o Render.com:**
   - Acesse: https://render.com
   - Crie uma conta
   - Conecte com GitHub
   - Crie um novo "Web Service"

3. **Configurações do Render:**
   - Repository: Selecione seu repositório
   - Branch: main
   - Build Command: `./build.sh`
   - Start Command: `java -jar target/jogos-tabuleiro-0.0.1-SNAPSHOT.jar`
   - Environment: `SPRING_PROFILES_ACTIVE=prod`

4. **Configure o banco PostgreSQL:**
   - No Render, crie um "PostgreSQL Database"
   - Copie a URL de conexão
   - Configure as variáveis de ambiente:
     - `DATABASE_URL`: URL do banco
     - `DB_USERNAME`: usuário do banco
     - `DB_PASSWORD`: senha do banco

### Variáveis de Ambiente para Produção

Configure no Render.com:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=postgresql://user:password@host:port/database
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
PORT=8080
```

### Monitoramento em Produção

1. **Logs do Render:**
   - Acesse o dashboard do Render
   - Vá em "Logs" para ver logs em tempo real

2. **Health Check:**
   - Configure endpoint: `/actuator/health`
   - Adicione dependência do Actuator se necessário

3. **Métricas:**
   - Configure monitoramento de CPU/memória
   - Configure alertas para falhas

---

## Conclusão

Este guia fornece todas as informações necessárias para executar o projeto "Loja de Jogos de Tabuleiro" em ambiente de desenvolvimento e produção. Siga os passos cuidadosamente e consulte a seção de solução de problemas em caso de dificuldades.

Para suporte adicional, consulte:
- Documentação do Spring Boot: https://spring.io/projects/spring-boot
- Documentação do Docker: https://docs.docker.com/
- Documentação do IntelliJ IDEA: https://www.jetbrains.com/help/idea/

**Autor:** Manus AI  
**Data:** Janeiro 2025  
**Versão:** 1.0

