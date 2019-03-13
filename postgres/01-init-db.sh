#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username postgres --dbname postgres <<-EOSQL
    CREATE USER angio_app WITH PASSWORD 'q1w2e3';
    CREATE DATABASE angio_app_db OWNER angio_app;
EOSQL
