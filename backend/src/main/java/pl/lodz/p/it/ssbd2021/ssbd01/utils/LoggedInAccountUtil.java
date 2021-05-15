package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.security.Principal;
import javax.ejb.Stateless;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;

@Stateless
public class LoggedInAccountUtil {
    @Context
    private SecurityContext securityContext;

    /**
     * Pobiera login zalogowanego użytkownika.
     *
     * @return login zalogowanego użytkownika
     */
    public String getLoggedInAccountLogin() {
        Principal loggedIn = securityContext.getCallerPrincipal();
        
        if (loggedIn == null) {
            return null;
        }
        
        return loggedIn.getName();
    }
}
