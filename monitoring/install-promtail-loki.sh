#!/bin/bash

# Este script instala o Loki (agregador de logs) e o Promtail (agente de coleta) usando Helm.
# Pré-requisitos: 'kubectl' e 'helm' devem estar instalados e configurados.

set -e

# --- Seção 1: Verificação de Pré-requisitos ---
echo "--- Verificando pré-requisitos ---"

if ! command -v kubectl &> /dev/null; then
    echo "Erro: O comando 'kubectl' não foi encontrado. Por favor, instale e configure o kubectl."
    exit 1
fi

if ! command -v helm &> /dev/null; then
    echo "Erro: O comando 'helm' não foi encontrado. Por favor, instale o Helm primeiro."
    exit 1
fi

kubectl cluster-info &> /dev/null || {
    echo "Erro: Não foi possível conectar ao cluster Kubernetes. Por favor, verifique a sua configuração do kubectl."
    exit 1
}

# --- Seção 2: Configuração do Repositório Helm ---
echo "--- Adicionando e atualizando o repositório da Grafana ---"

helm repo add grafana https://grafana.github.io/helm-charts || {
    echo "Falha ao adicionar o repositório. Verifique sua conexão com a internet."
    exit 1
}
helm repo update

# --- Seção 3: Variáveis de Instalação ---
NAMESPACE="monitoring"
LOKI_RELEASE_NAME="loki"
PROMTAIL_RELEASE_NAME="promtail"
VALUES_FILE="./promtail-values.yaml"

# --- Seção 4: Instalação do Loki ---
echo "--- Instalando o Loki ---"

echo "Verificando se o namespace '$NAMESPACE' existe..."
if ! kubectl get namespace "$NAMESPACE" &> /dev/null; then
    echo "Namespace '$NAMESPACE' não encontrado. Criando..."
    kubectl create namespace "$NAMESPACE"
fi

if helm status "$LOKI_RELEASE_NAME" -n "$NAMESPACE" &> /dev/null; then
    echo "O release do Loki ('$LOKI_RELEASE_NAME') já está instalado. Pulando a instalação."
else
    echo "Aplicando o loki-pvc"
    kubectl apply -f loki-pvc.yaml
    echo "Instalando o chart do Loki SingleBinary"
    helm install loki grafana/loki -f loki-values.yaml --namespace monitoring --create-namespace
    echo "Loki instalado com sucesso."
fi

# --- Seção 5: Instalação do Promtail ---
echo "--- Instalando o Promtail com promtail-values.yaml ---"

# Verifica se o release já existe
if helm status "$PROMTAIL_RELEASE_NAME" -n "$NAMESPACE" &> /dev/null; then
    echo "O release do Promtail ('$PROMTAIL_RELEASE_NAME') já está instalado. Pulando a instalação."
else
    echo "Instalando o chart do Promtail com values.yaml personalizado..."
    helm install "$PROMTAIL_RELEASE_NAME" grafana/promtail \
        --namespace "$NAMESPACE" \
        -f "$VALUES_FILE" \
        --wait
    echo "Promtail instalado com sucesso."
fi

# --- Seção 6: Instruções Finais ---
echo ""
echo "---------------------------------------------------------"
echo "Instalação de Loki e Promtail concluída com sucesso!"
echo "---------------------------------------------------------"
echo ""
echo "Para verificar os pods, execute:"
echo "kubectl get pods -n $NAMESPACE"
echo ""
echo "O Promtail agora está coletando logs de todos os nós e enviando para o Loki."
echo "Você pode usar um dashboard como o Grafana para visualizar esses logs."
echo "Se você instalou o Prometheus Stack anteriormente, pode conectar o Grafana a essa instância do Loki."
echo ""
echo "Script finalizado."
