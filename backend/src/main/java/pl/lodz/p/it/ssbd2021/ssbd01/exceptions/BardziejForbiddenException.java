package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import javax.ws.rs.WebApplicationException;

public class BardziejForbiddenException extends RuntimeException {
    public BardziejForbiddenException() {
        super();
    }
    public BardziejForbiddenException(String message) {
        super(message);
    }

    protected BardziejForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
