########################################################################################################################

# Utiliser la Java Virtual Machine (JVM) "amazoncorretto" (version Java 17) sur la distribution linux "Alpine"

FROM amazoncorretto:17-alpine

########################################################################################################################

LABEL maintainer="Olivier MORLOTTI <o.morlotti@gmail.com>"

LABEL description="Le MUR"

########################################################################################################################

# Variables d'environnement pour configurer LeMur

ENV DB_URL="jdbc:mysql://localhost:8889/le_mur?useSSL=false&serverTimezone=UTC"
ENV DB_USERNAME="root"
ENV DB_PASSWORD="root"

ENV WC_URL="http://localhost:8888/wp-json/wc/v3/"

ENV JWT_SECRET="asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4"
ENV JWT_EXPIRATION_MS="86400000"

########################################################################################################################

RUN ["mkdir", "/LeMur/"]

########################################################################################################################

# Copie de /LeMur/LeMur.jar

COPY target/LeMur-1.0.jar /LeMur/LeMur.jar

########################################################################################################################

# Copie de /docker-entrypoint.sh

COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN ["chmod", "a+x", "/docker-entrypoint.sh"]

########################################################################################################################

# Le port exposé à l'extérieur de contenneur est 8080

EXPOSE 8080

########################################################################################################################

# Le dossier courant est /LeMur/

WORKDIR /LeMur/

########################################################################################################################

# Le script shell à lancer au démarage du contenneur

ENTRYPOINT ["/docker-entrypoint.sh"]

########################################################################################################################
