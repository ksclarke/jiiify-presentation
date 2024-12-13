
FROM eclipse-temurin:21.0.5_11-jdk-alpine

LABEL Maintainer="Kevin S. Clarke <ksclarke@ksclarke.io>"
LABEL ContainerName="${project.name}" ContainerSourceCode="${project.url}"

RUN apk add --no-cache nano curl && \
  addgroup -S jpv3 && \
  adduser -S jpv3 -G jpv3 -s bash

COPY --chown=jpv3:jpv3 --chmod=0400 "maven/${project.name}-${project.version}-uber.jar" "/opt/${project.name}.jar"
COPY --chown=jpv3:jpv3 --chmod=0400 "imports.jsh" "/etc/jshell/imports.jsh"

EXPOSE 8888

USER jpv3

CMD ["sh", "-c", "exec java ${JAVA_OPTS} -jar /opt/${project.name}.jar"]
