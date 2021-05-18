package pl.lodz.p.it.ssbd2021.ssbd01.auth;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/jdbc/ssbd01auth",
        callerQuery = "#{'select password from glassfish_auth_view where login = ?'}",
        groupsQuery = "select level from glassfish_auth_view  where login = ?",
        hashAlgorithm = AuthHashImpl.class
)

@RolesAllowed({I18n.RECEPTIONIST, I18n.DOCTOR, I18n.ADMIN, I18n.PATIENT})
@ApplicationScoped
public class IdentityStoreConfig {
}
