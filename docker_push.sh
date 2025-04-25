#!/bin/bash
docker login -u yusufozer1 -p $DOCKER_PASSWORD
docker push yusufozer1/ecommerce_builder-demo:latest
docker push yusufozer1/ecommerce_builder-editor:latest
docker logout