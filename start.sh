#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$SCRIPT_DIR/backend"
FRONTEND_DIR="$SCRIPT_DIR/frontend"
MAVEN_BIN="/tmp/apache-maven-3.9.9/bin"

# Verifica se o Maven está disponível
if ! command -v mvn &> /dev/null; then
    if [ -f "$MAVEN_BIN/mvn" ]; then
        export PATH="$MAVEN_BIN:$PATH"
    else
        echo "❌ Maven não encontrado. Baixando..."
        mkdir -p /tmp
        wget -q -O /tmp/maven.tar.gz "https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz"
        tar -xzf /tmp/maven.tar.gz -C /tmp
        export PATH="$MAVEN_BIN:$PATH"
    fi
fi

# Verifica se o Flutter está disponível
FLUTTER_BIN=""
for path in \
    "$(command -v flutter 2>/dev/null)" \
    "$HOME/development/flutter/bin/flutter" \
    "$HOME/flutter/bin/flutter" \
    "$HOME/.local/share/flutter/bin/flutter" \
    "/opt/flutter/bin/flutter" \
    "/usr/local/flutter/bin/flutter"; do
    if [ -f "$path" ] && [ -x "$path" ]; then
        FLUTTER_BIN="$path"
        break
    fi
done

echo "============================================"
echo "  🚀 Device Manager - Inicializador"
echo "============================================"
echo ""

# Compila e inicia o backend
echo "📦 Compilando backend (Java 21 + Spring Boot)..."
cd "$BACKEND_DIR"
mvn clean package -DskipTests -q

echo ""
echo "🟢 Iniciando backend na porta 8080..."
nohup java -jar target/devicemanager-0.0.1-SNAPSHOT.jar > "$SCRIPT_DIR/backend.log" 2>&1 &
BACKEND_PID=$!
echo "   PID: $BACKEND_PID"
echo "   Log: $SCRIPT_DIR/backend.log"

sleep 3

if kill -0 $BACKEND_PID 2>/dev/null; then
    echo "   ✅ Backend iniciado com sucesso!"
    echo "   📋 API: http://localhost:8080/api"
    echo "   🗄️  H2 Console: http://localhost:8080/h2-console"
else
    echo "   ❌ Falha ao iniciar o backend. Verifique o log."
    exit 1
fi

# Salva o PID para facilitar
echo $BACKEND_PID > "$SCRIPT_DIR/.backend.pid"

# Finaliza o backend ao sair do script
cleanup() {
    echo ""
    echo "🛑 Parando backend (PID: $BACKEND_PID)..."
    kill $BACKEND_PID 2>/dev/null || true
    rm -f "$SCRIPT_DIR/.backend.pid"
    echo "   ✅ Backend finalizado."
}
trap cleanup EXIT INT TERM

echo ""

# Frontend
if [ -n "$FLUTTER_BIN" ] && [ -f "$FLUTTER_BIN" ]; then
    echo "📱 Flutter detectado: $FLUTTER_BIN"
    echo ""
    echo "🚀 Iniciando frontend Flutter..."
    echo "   Pressione Ctrl+C para parar ambos (backend + frontend)"
    echo ""
    cd "$FRONTEND_DIR"
    $FLUTTER_BIN run
else
    echo "⚠️  Flutter não detectado automaticamente."
    echo "   Certifique-se de que o Flutter está instalado e no PATH."
    echo ""
    echo "============================================"
    echo "  ✅ Backend rodando em background!"
    echo "============================================"
    echo ""
    echo "Comandos úteis:"
    echo "   Parar backend: kill $BACKEND_PID"
    echo "   Ver log backend: tail -f $SCRIPT_DIR/backend.log"
    echo ""
    # Mantém o script rodando para o trap funcionar
    wait $BACKEND_PID
fi
