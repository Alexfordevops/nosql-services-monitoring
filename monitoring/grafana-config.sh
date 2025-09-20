#!/bin/bash

echo "Aplicando arquivo de configuração no grafana para subpath /grafana no ingress"
helm upgrade prometheus-stack prometheus-community/kube-prometheus-stack \
  -n monitoring \
  -f grafana-values.yaml