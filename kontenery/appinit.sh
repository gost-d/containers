#!/bin/bash

docker network create mynet
docker build -t example/rabbitmq:v1.0 ./rabbitmq
docker run --network mynet --name rabbitmq -d example/rabbitmq:v1.0
docker build -t example/publisher:v1.0 ./publisher 
docker run -d --network mynet --name publisher -p 9292:9292 -e HOST=rabbitmq --cpus=".5" -m 512m example/publisher:v1.0
docker run --network mynet --name redishost -d redis 
docker build -t example/receiver:v1.0 ./receiver
docker run -d --network mynet --name receiver -e RABIT_HOST=rabbitmq -e REDIS_HOST=redishost --cpus=".5" -m 512m example/receiver:v1.0
docker build -t example/itemcounter:v1.0 ./itemCounter
docker run -d -p 9090:9090 --name itemcounter --network mynet -e REDIS_HOST='redishost:6379' --cpus=".5" -m 512m example/itemcounter:v1.0