package pl.lodz.p.it.ssbd2021.ssbd01.security;

import java.util.Map;

/**
 * Interfejs funkcjonalności dodawania wartości ETag do nagłówka żądania.
 */
public interface SignableEntity {
    /**
     * Metoda tworząca Etag.
     *
     * @return ciąg znaków do stworznia Etag
     */
    Map<String,String> getPayload();


}
