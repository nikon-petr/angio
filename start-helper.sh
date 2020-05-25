#!/bin/bash

COMMAND='docker-compose -f docker-compose.yml'

case $1 in
    # services: postgres
    'db-only')
        COMMAND="${COMMAND}";;
    # services: postgres, artemis, analyse-executor
    'dev')
        COMMAND="${COMMAND} -f docker-compose.local.dev.yml";;
    # services: postgres, artemis, analyse-executor, angio-backend
    'prod')
        COMMAND="${COMMAND} -f docker-compose.local.prod.yml";;
esac

if [ -z "$2" ] && [ -z "$3" ]; then
    eval "${COMMAND} up"
elif [ $2 = "build" ]; then
    eval "${COMMAND} build $3"
fi
