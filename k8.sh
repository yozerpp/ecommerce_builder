#!/bin/bash
function forward-port() {
  until ps aux | egrep "kubectl.*$1 $2" | egrep -v grep > /dev/null; do
      kubectl port-forward $1 $2 </dev/null &
      sleep 2
  done
}
ports=("svc/demo-service:8080:8080" "svc/demo-service:5005:5005" "svc/editor-service:8081:8080" "svc/editor-service:5006:5005")
function forward-ports(){
  for p in ${ports[@]}; do
    forward-port ${p%%:*} ${p#*:}
  done
}
if [[ -z "$1" ]]; then
  echo "usage: $0 <up/down>"
  exit 1
fi
while (($#>0))
do
if [[ "$1" == "start" ]]; then
minikube start --cpus=max --mount --mount-string=$(pwd)/kube/mount:/mnt/kube --insecure-registry="host.minikube.internal:5000"
elif [[ "$1" == "stop" ]]; then
minikube stop && minikube delete
elif [[ "$1" == "up" ]]; then
kubectl apply -f kube/config-map.yaml
kubectl apply -f kube/secrets.yaml
kubectl apply -f kube/ecommercedb.yaml
kubectl apply -f kube/demo.yaml
kubectl apply -f kube/editor.yaml
kubectl apply -f kube/init-db.yaml
forward-ports
elif [ "$1" == "down" ]; then
kubectl delete -f kube/config-map.yaml
kubectl delete -f kube/secrets.yaml
kubectl delete -f kube/demo.yaml
kubectl delete -f kube/editor.yaml
kubectl delete -f kube/ecommercedb.yaml
kubectl delete -f kube/init-db.yaml
IFS=' '
for p in $(ps aux | egrep "kubectl port-forward" | egrep -v grep); do
  sudo kill $(echo $p|awk '{print $2}')
done;
fi
shift
done