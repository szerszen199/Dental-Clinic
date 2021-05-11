package pl.lodz.p.it.ssbd2021.ssbd01.auth;

import pl.lodz.p.it.ssbd2021.ssbd01.common.RolesStringsTmp;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/jdbc/ssbd01auth",
        callerQuery = "#{'select password from glassfish_auth_view where login = ?'}",
        groupsQuery = "select level from glassfish_auth_view  where login = ?",
        hashAlgorithm = AuthHashImpl.class
)

@DeclareRoles({RolesStringsTmp.user, RolesStringsTmp.admin, RolesStringsTmp.doctor, RolesStringsTmp.receptionist})
@ApplicationScoped
public class IdentityStoreConfig {
}
