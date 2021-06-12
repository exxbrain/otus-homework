FROM gradle:6.7.1-jdk11 AS builder
RUN mkdir /build
COPY registration-provider /build/
RUN cd /build && gradle build

FROM gradle:6.7.1-jdk11 AS builder1
RUN mkdir /build
COPY kafka-listener-provider-ear /build/
RUN cd /build && gradle ear

FROM jboss/keycloak:11.0.3
COPY --from=builder /build/build/libs/*.jar /opt/jboss/keycloak/standalone/deployments/registration-provider.jar
COPY --from=builder1 /build/build/libs/*.ear /opt/jboss/keycloak/standalone/deployments/kafka-listener-provider.ear
COPY profile.properties /opt/jboss/keycloak/standalone/configuration/profile.properties
COPY realm.json /realm/realm.json