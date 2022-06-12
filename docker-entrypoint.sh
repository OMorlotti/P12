#!/bin/sh

########################################################################################################################

java \
-Dfile.encoding=UTF-8 \
-Dspring.datasource.url="$DB_URL" \
-Dspring.datasource.username="$DB_USERNAME" \
-Dspring.datasource.password="$DB_PASSWORD" \
-Dwoocommerce.base_url="$WC_URL" \
-Dapp.jwt.secret="$JWT_SECRET" \
-Dapp.jwt.expirationMs="$JWT_EXPIRATION_MS" \
-jar /LeMur/LeMur.jar xyz.morlotti.lemur.Application

########################################################################################################################
