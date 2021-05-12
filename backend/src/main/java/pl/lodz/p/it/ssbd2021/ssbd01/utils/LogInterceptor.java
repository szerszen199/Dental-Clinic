package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInterceptor {
    private static final Logger LOGGER = Logger.getLogger(LogInterceptor.class.getName());

    //TODO: Zmuszenie sessionContextu do poprawnego injectowania

    //    @Resource
    //    private SessionContext sessionContext;

    /**
     * Dodaje logi do metod.
     *
     * @param context Invocation context.
     * @return Zwraca context.proceed()
     * @throws Exception Wyjątki.
     */
    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        //        Handler handler = new FileHandler("pliczek.txt");
        //        LOGGER.addHandler(handler);
        //        LOGGER.addHandler(new ConsoleHandler());
        String returnedValue = null;
        Level logLevel = Level.INFO;
        try {
            Object result = context.proceed();
            if (result != null) {
                returnedValue = "Zwrócono: " + result.toString();
            } else {
                returnedValue = "Nie zwrócono żadnej wartości";
            }
            return result;
        } catch (Exception e) {
            returnedValue = "Zgłoszono wyjątek: " + e.toString() + " Przyczyna: " + e.getCause();
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
                //TODO: Wypisywanie użytkownika
                LOGGER.log(logLevel, "Użytkownik: " + "Metoda: " + context.getMethod().toGenericString() + "Parametry: " + parameters + "Zwrócona wartość: " + returnedValue);
            }
        }
    }
}
