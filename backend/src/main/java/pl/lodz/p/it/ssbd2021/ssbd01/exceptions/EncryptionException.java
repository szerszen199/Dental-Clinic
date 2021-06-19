package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

import pl.lodz.p.it.ssbd2021.ssbd01.common.I18n;

/**
 * Typ wyjatków dla enkrypcji.
 */
public class EncryptionException extends AppBaseException {
    /**
     * Tworzy nową instancję klasy Encryption exception.
     *
     * @param message wiadomość
     */
    protected EncryptionException(String message) {
        super(message);
    }

    /**
     * Tworzy nową instancję klasy Encryption exception.
     *
     * @param message wiadomość
     * @param cause   powód
     */
    protected EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Wyjątek z wiadomością informującą o niepowodzeniu podczas szyfrowania wiadomości.
     *
     * @return wyjątek typu EncryptionException
     */
    public static EncryptionException encryptingFailed() {
        return new EncryptionException(I18n.ENCRYPTING_FAILED);
    }

}
