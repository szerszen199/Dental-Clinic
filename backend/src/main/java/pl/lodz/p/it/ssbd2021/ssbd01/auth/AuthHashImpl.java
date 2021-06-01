package pl.lodz.p.it.ssbd2021.ssbd01.auth;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.HashGenerator;

import javax.inject.Inject;
import javax.security.enterprise.identitystore.PasswordHash;
import java.util.Map;
import java.util.Objects;

/**
 * Typ Auth hash implementacja hash generatora do generowania hashu hasła podanego w postaci String.
 */
public class AuthHashImpl implements PasswordHash {
    @Inject
    private HashGenerator hashGenerator;

    @Override
    public void initialize(Map<String, String> parameters) {
    }

    @Override
    public String generate(char[] password) {
        return hashGenerator.generateHash(new String(password));
    }

    @Override
    public boolean verify(char[] password, String hashedPassword) {
        return Objects.equals(hashGenerator.generateHash(new String(password)), hashedPassword);
    }
}
