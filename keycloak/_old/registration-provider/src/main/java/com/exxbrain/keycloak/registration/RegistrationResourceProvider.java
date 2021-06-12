package com.exxbrain.keycloak.registration;

import org.keycloak.common.ClientConnection;
import org.keycloak.events.EventBuilder;
import org.keycloak.events.EventStoreProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.models.utils.RepresentationToModel;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RolesRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.services.resource.RealmResourceProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class RegistrationResourceProvider implements RealmResourceProvider {
    private final KeycloakSession session;

    public RegistrationResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserToRegister userToRegister) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userToRegister.getUsername());
        user.setEmail(userToRegister.getEmail());
        user.setFirstName(userToRegister.getFirstName());
        user.setLastName(userToRegister.getLastName());
        user.setEnabled(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userToRegister.getPassword());
        user.setCredentials(Collections.singletonList(credentialRepresentation));

        user.setClientRoles(Map.of("account", Arrays.asList("view-profile", "manage-account")));

        RealmModel realm = session.getContext().getRealm();
        UserModel model = RepresentationToModel.createUser(session, realm, user);
        UserRepresentation representation =
                ModelToRepresentation
                        .toRepresentation(session, session.getContext().getRealm(), model);

        new EventBuilder(realm, session, session.getContext().getConnection())
                .event(EventType.REGISTER).user(model).detail("user", model.toString()).success();

        return Response.status(Response.Status.CREATED).entity(representation).build();
    }

    @Override
    public void close() {
    }
}
