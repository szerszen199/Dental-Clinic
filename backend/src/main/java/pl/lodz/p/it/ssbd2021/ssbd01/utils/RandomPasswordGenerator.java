package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import org.apache.commons.lang3.RandomStringUtils;

import javax.annotation.ManagedBean;

/**
 * Klasa generowania losowego hasła.
 */
@ManagedBean
public class RandomPasswordGenerator {

    /**
     * Generuje alfanumeryczny ciąg znaków o zadanej długości.
     *
     * @param length długość generowanego ciągu znaków
     * @return wygenerowany alfanumeryczny ciąg znaków
     */
    public String generate(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
