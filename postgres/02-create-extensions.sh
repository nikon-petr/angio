#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username postgres --dbname angio_app_db <<-EOSQL
    CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
EOSQL
