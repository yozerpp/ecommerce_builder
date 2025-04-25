#!/bin/bash
if [[ -z "$1" ]]; then
  echo "usage: $0 <up/down>"
  exit 1
fi
while (($#>0))
do
if [[ "$1" == "start" ]]; then
minikube start --cpus=max --mount --mount-string=$(pwd)/kube:/mnt/kube
elif [[ "$1" == "stop" ]]; then
minikube stop && minikube delete
elif [[ "$1" == "up" ]]; then
kubectl apply -f kube/config-map.yaml
kubectl apply -f kube/secrets.yaml
kubectl apply -f kube/ecommercedb.yaml
kubectl apply -f kube/demo.yaml
kubectl apply -f kube/editor.yaml
elif [ "$1" == "down" ]; then
kubectl delete -f kube/config-map.yaml
kubectl delete -f kube/secrets.yaml
kubectl delete -f kube/demo.yaml
kubectl delete -f kube/editor.yaml
kubectl delete -f kube/ecommercedb.yaml
fi
shift
done