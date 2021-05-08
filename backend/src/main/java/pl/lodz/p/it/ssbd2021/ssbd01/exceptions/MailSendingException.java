package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

public class MailSendingException extends BaseException {
    
    private static final String ACTIVATION_LINK = "Activation link couldn't be sent.";
    
    protected MailSendingException(String message) {
        super(message);
    }

    protected MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public static MailSendingException activationLink() {
        return new MailSendingException(ACTIVATION_LINK);
    }
}
