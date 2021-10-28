package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa interceptora logów.
 */
public class LogInterceptor {
    private static final Logger LOGGER = Logger.getLogger(LogInterceptor.class.getName());

    @Inject
    private LoggedInAccountUtil loggedInAccountUtil;

    /**
     * Dodaje logi do metod.
     *
     * @param context Invocation context.
     * @return Zwraca context.proceed()
     * @throws Exception Wyjątki.
     */
    @AroundInvoke
    @PermitAll
    public Object intercept(InvocationContext context) throws Exception {

        String returnedValue = null;
        Level logLevel = Level.INFO;
        try {
            Object result = context.proceed();
            if (result != null) {
                returnedValue = "Zwrócono: " + result;
            } else {
                returnedValue = "Nie zwrócono żadnej wartości";
            }
            return result;
        } catch (Exception e) {
            returnedValue = "Zgłoszono wyjątek: " + e + " Przyczyna: " + e.getCause();
            logLevel = Level.WARNING;
            throw e;
        } finally {
            {
                String parameters;
                if (context.getParameters() != null) {
                    parameters = Arrays.toString(context.getParameters());
                } else {
                    parameters = " ";
                }
                //FIXME
                LOGGER.log(logLevel, "Użytkownik: " + loggedInAccountUtil.getLoggedInAccountLogin()
                        + " Metoda: " + context.getMethod().toGenericString() + " Parametry: " + parameters + " Zwrócona wartość: " + returnedValue);
            }
        }
    }
}
