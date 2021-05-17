package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;

@Stateless
public class LoggedInAccountUtil {
    @Inject
    private SecurityContext securityContext;

    public String getLoggedInAccountLogin() {
        return securityContext.getCallerPrincipal().getName();
    }
}
