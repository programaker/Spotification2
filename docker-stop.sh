#! /bin/bash
docker stop "$(docker ps --filter "name=spotification2" --format '{{.ID}}')"
