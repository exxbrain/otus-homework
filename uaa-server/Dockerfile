FROM cloudfoundry/uaa:75.2.0

ENV UAA_CONFIG_PATH=/uaa
ENV spring_profiles=default,postgresql

COPY uaa.yml /uaa/uaa.yml
COPY log4j2.properties /workspace/WEB-INF/classes/log4j2.properties