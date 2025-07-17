#!/bin/bash

# Script de build para Render.com

echo "🚀 Iniciando build da aplicação..."

# Dar permissão de execução ao Maven wrapper
chmod +x ./mvnw

echo "📦 Baixando dependências..."
./mvnw dependency:go-offline -B

echo "🔨 Compilando aplicação..."
./mvnw clean package -DskipTests

echo "✅ Build concluído com sucesso!"

# Verificar se o JAR foi criado
if [ -f "target/jogos-tabuleiro-0.0.1-SNAPSHOT.jar" ]; then
    echo "✅ JAR criado: target/jogos-tabuleiro-0.0.1-SNAPSHOT.jar"
    ls -la target/jogos-tabuleiro-0.0.1-SNAPSHOT.jar
else
    echo "❌ Erro: JAR não foi criado!"
    exit 1
fi

