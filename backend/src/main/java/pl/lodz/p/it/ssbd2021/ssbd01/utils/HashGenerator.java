package pl.lodz.p.it.ssbd2021.ssbd01.utils;

public interface HashGenerator {
    /**
     * Generator skrótu hash.
     *
     * @param input dane wejściowe
     * @return string
     */
    public String generateHash(String input);
}
