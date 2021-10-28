package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;

/**
 * Klasa narzędzia zalogowanego kotna.
 */
@Stateless
public class LoggedInAccountUtil {
    @Resource(name = "sessionContext")
    private SessionContext securityContext;

    /**
     * Pobiera login zalogowanego użytkownika.
     *
     * @return login zalogowanego użytkownika
     */
    public String getLoggedInAccountLogin() {
        if (securityContext == null || securityContext.getCallerPrincipal() == null) {
            return null;
        }
        return securityContext.getCallerPrincipal().getName();
    }


    public boolean isCallerInRole(String level) {
        return securityContext.isCallerInRole(level);
    }
}
