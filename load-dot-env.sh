#! /bin/bash

# Usage:
# source load-dot-env.sh

export $(cat .env | xargs)
