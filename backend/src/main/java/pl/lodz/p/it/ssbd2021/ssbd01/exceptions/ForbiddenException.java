package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import javax.ws.rs.WebApplicationException;

public class ForbiddenException extends WebApplicationException {
    public ForbiddenException() {
        super();
    }
    public ForbiddenException(String message) {
        super(message);
    }

    protected ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
