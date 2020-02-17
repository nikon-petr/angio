#!/bin/bash

COMMAND='docker-compose -f docker-compose.yml'

case $1 in
    'dev')
        COMMAND="${COMMAND}";;
    'prod')
        COMMAND="${COMMAND} -f docker-compose.local.prod.yml";;

esac

eval "${COMMAND} up"
