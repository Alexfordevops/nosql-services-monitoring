#!/bin/bash

# Este script instala o Helm (se necessário) e o Prometheus Stack.
# Requer o 'kubectl' configurado e conectado a um cluster Kubernetes.

set -e

# --- Seção 1: Verificação de Pré-requisitos ---
echo "--- Verificando pré-requisitos ---"

if ! command -v kubectl &> /dev/null; then
    echo "Erro: O comando 'kubectl' não foi encontrado. Por favor, instale e configure o kubectl."
    exit 1
fi

kubectl cluster-info &> /dev/null || {
    echo "Erro: Não foi possível conectar ao cluster Kubernetes."
    echo "Por favor, verifique se seu 'kubectl' está configurado corretamente."
    exit 1
}

# --- Seção 2: Instalação do Helm ---
echo "--- Verificando e instalando o Helm ---"

if ! command -v helm &> /dev/null; then
    echo "Helm não encontrado. Instalando..."
    curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
    chmod 700 get_helm.sh
    sudo bash get_helm.sh
    rm get_helm.sh
    echo "Helm instalado com sucesso."
else
    echo "Helm já está instalado. Prosseguindo..."
fi

# --- Seção 3: Instalação do Prometheus Stack ---
echo "--- Iniciando a instalação do Prometheus Stack ---"

NAMESPACE="monitoring"
RELEASE_NAME="prometheus-stack"

echo "Adicionando o repositório oficial do Prometheus Community..."
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts || {
    echo "Falha ao adicionar o repositório. Verifique sua conexão."
    exit 1
}
helm repo update
echo "Repositórios atualizados."

echo "Verificando se o namespace '$NAMESPACE' existe..."
if ! kubectl get namespace "$NAMESPACE" &> /dev/null; then
    echo "Namespace '$NAMESPACE' não encontrado. Criando..."
    kubectl create namespace "$NAMESPACE"
fi

echo "Verificando se o chart '$RELEASE_NAME' já está instalado no namespace '$NAMESPACE'..."
if helm status "$RELEASE_NAME" -n "$NAMESPACE" &> /dev/null; then
    echo "O chart '$RELEASE_NAME' já está instalado. O script será finalizado para evitar reinstalação."
    echo "Se desejar reinstalar, execute o comando: 'helm uninstall $RELEASE_NAME -n $NAMESPACE'"
    exit 0
fi

echo "Iniciando a instalação do Prometheus Stack..."
helm install "$RELEASE_NAME" prometheus-community/kube-prometheus-stack --namespace "$NAMESPACE" --wait
echo "Instalação do Prometheus Stack concluída com sucesso."

echo "Aplicando arquivo de configuração no grafana para subpath /grafana no ingress"
helm upgrade prometheus-stack prometheus-community/kube-prometheus-stack \
  -n monitoring \
  -f grafana-values.yaml
  
# --- Seção 4: Instruções Pós-Instalação ---
echo ""
echo "--- Instruções para Acessar o Grafana ---"
echo ""
echo "A instalação foi concluída. Agora você pode acessar o painel do Grafana."
echo ""
echo "1. Para obter a senha do usuário 'admin', execute o seguinte comando:"
echo "kubectl get secret $RELEASE_NAME-grafana -n $NAMESPACE -o jsonpath=\"{.data.admin-password}\" | base64 -d"
echo ""
echo "2. Para acessar o dashboard, execute o comando de port-forwarding em um novo terminal:"
echo "kubectl port-forward service/$RELEASE_NAME-grafana 8080:80 -n $NAMESPACE"
echo ""
echo "Após executar o comando acima, acesse http://localhost:8080 no seu navegador."
echo "Use o usuário 'admin' e a senha que você obteve no passo 1."
echo ""
echo "----------------------------------------"
echo "Script finalizado."