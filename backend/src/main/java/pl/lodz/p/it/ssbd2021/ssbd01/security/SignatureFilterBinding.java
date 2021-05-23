package pl.lodz.p.it.ssbd2021.ssbd01.security;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Adnotacja łącząca EtagFilter z metodami.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureFilterBinding {
}
