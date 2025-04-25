#!/bin/bash
docker login -u yusufozer1 -p $DOCKER_PASSWORD
docker build -t yusufozer1/$1 $2
docker logout