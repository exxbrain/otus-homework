#
# Build stage
#
FROM openjdk:11.0-jdk-slim AS builder
WORKDIR /opt/builder
# optimization step, to download dependencies
COPY *.gradle gradle.* gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew
RUN ./gradlew --no-daemon build || return 0
# building
COPY . .
RUN chmod +x gradlew
RUN ./gradlew --no-daemon build --stacktrace
RUN cd /opt/builder/build/libs/ && rm *-plain.jar

#
# Package stage
#
FROM openjdk:11-jre

WORKDIR /opt/app
COPY --from=builder /opt/builder/build/libs/*.jar standalone.jar

ENV APP_RMI_HOSTNAME 0.0.0.0

ENV JAVA_OPTS -Djava.net.preferIPv4Stack=true \
              -XX:MaxRAMPercentage=60.0

ENV APP_OPTS -Duser.timezone=Europe/Moscow \
             -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005

ENV SERVER_PORT 80

EXPOSE 80 5005
ENTRYPOINT exec java $JAVA_OPTS $APP_OPTS -Djava.rmi.server.hostname=$APP_RMI_HOSTNAME -jar standalone.jar