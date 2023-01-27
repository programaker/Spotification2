#! /bin/bash

newest_image="$(docker images spotification2 --format '{{.ID}}' | head -n 1)"
echo "Running image $newest_image..."

docker run --rm -d \
-p 8080:8080 \
--name spotification2 \
--env-file .env \
"$newest_image"
