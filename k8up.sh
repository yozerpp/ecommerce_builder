#!/bin/bash
kubectl apply -f kube/config-map.yaml
kubectl apply -f kube/secrets.yaml
kubectl apply -f kube/ecommercedb.yaml
kubectl apply -f kube/demo.yaml
kubectl apply -f kube/editor.yaml
