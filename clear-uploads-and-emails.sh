#!/usr/bin/env bash

cd ./angio-backend/src/main/resources/static/uploads
rm -f {*.png,*.bmp}

cd ../email
rm -r -- */
