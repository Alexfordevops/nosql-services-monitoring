#!/bin/bash

# Start Minikube with 7800MB of memory
echo "Starting Minikube with 7800MB of memory..."
minikube start --memory=7800mb

# Wait for Minikube to be fully ready
echo "Waiting for Minikube to be ready..."

# Set the current context to the 'nosql' namespace
echo "Setting current context to 'nosql' namespace..."
kubectl config set-context --current --namespace=nosql

# User kubernetes docker
echo "Setting kubernetes docker"
eval $(minikube docker-env)

echo "Script execution complete."
