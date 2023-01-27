#! /bin/bash
docker logs -f "$(docker ps --filter "name=spotification2" --format '{{.ID}}')"
