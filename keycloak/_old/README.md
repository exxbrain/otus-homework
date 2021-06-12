### Relevant Articles:

- [Kafka integration example](https://github.com/akoserwal/keycloak-integrations/blob/master/keycloak-spi-kafka)
- [Extensions](https://www.keycloak.org/docs/latest/server_development/#_extensions)
- [Providers](https://github.com/keycloak/keycloak/tree/master/examples/providers/rest)
- [Account management](https://www.marcus-povey.co.uk/2020/10/12/using-the-keycloak-accounts-management-api/)
- [Registration example](https://www.iditect.com/how-to/51709957.html)
- [Keycloak Helm charts](https://github.com/codecentric/helm-charts/tree/master/charts/keycloak)
- [Keycloak Docker](https://hub.docker.com/r/jboss/keycloak)
- [Medium article](https://medium.com/devops-dudes/keycloak-for-identity-and-access-management-9860a994bf0)
- [Keycloak Embedded in a Spring Boot Application](https://www.baeldung.com/keycloak-embedded-in-spring-boot-app)

- [Accessing Keycloak Endpoints Using Postman](https://www.baeldung.com/postman-keycloak-endpoints)

```json
{
    "issuer": "http://localhost:8083/auth/realms/arch",
    "authorization_endpoint": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/auth",
    "token_endpoint": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/token",
    "token_introspection_endpoint": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/token/introspect",
    "userinfo_endpoint": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/userinfo",
    "end_session_endpoint": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/logout",
    "jwks_uri": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/certs",
    "check_session_iframe": "http://localhost:8083/auth/realms/arch/protocol/openid-connect/login-status-iframe.html",
    "grant_types_supported": [...],
    ...
    "registration_endpoint": "http://localhost:8083/auth/realms/baeldung/clients-registrations/openid-connect",
    ...
    "introspection_endpoint": "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect/token/introspect"
}
```