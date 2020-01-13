#! /bin/bash

DOCKER_TAG=mongo:4.2-bionic
DOCKER_VOLUME=$(pwd)/mongodb

mkdir mongodb

docker run \
    --rm \
    --name mongodb \
    -p 27017:27017 \
    -v $DOCKER_VOLUME:/data/db \
    -d \
    $DOCKER_TAG
