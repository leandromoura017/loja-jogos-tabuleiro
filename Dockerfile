# Usar uma imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do Maven wrapper
COPY mvnw .
COPY .mvn .mvn

# Copiar pom.xml
COPY pom.xml .

# Dar permissão de execução ao Maven wrapper
RUN chmod +x ./mvnw

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src src

# Construir a aplicação
RUN ./mvnw clean package -DskipTests

# Expor porta 8080
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/jogos-tabuleiro-0.0.1-SNAPSHOT.jar"]

