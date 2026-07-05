#!/bin/bash
# Corre el backend localmente cargando las variables de .env (no se sube a git)
set -a
source "$(dirname "$0")/.env"
set +a
./mvnw spring-boot:run
