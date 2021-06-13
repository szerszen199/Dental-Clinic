package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

public class EncryptionException extends AppBaseException {
    protected EncryptionException(String message) {
        super(message);
    }

    protected EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EncryptionException encryptingFailed() {
        return new EncryptionException(I18n.ENCRYPTING_FAILED);
    }

}
