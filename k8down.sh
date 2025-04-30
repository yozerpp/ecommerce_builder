#!/bin/bash
kubectl delete -f kube/config-map.yaml
kubectl delete -f kube/secrets.yaml
kubectl delete -f kube/demo.yaml
kubectl delete -f kube/editor.yaml
kubectl delete -f kube/ecommercedb.yaml
