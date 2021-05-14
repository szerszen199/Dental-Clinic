package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.ejb.Stateless;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;

@Stateless
public class LoggedInAccountUtil {
    @Context
    private SecurityContext securityContext;

    public String getLoggedInAccountLogin() {
        return securityContext.getCallerPrincipal().getName();
    }
}
