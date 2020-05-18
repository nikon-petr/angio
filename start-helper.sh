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

eval "${COMMAND} up"
