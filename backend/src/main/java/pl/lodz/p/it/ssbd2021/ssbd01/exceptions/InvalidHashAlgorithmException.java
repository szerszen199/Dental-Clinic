package pl.lodz.p.it.ssbd2021.ssbd01.exceptions;

/**
 * Typ Invalid hash algorithm exception - wyjątek nieporawidłowego algorytmu hashowania.
 */
public class InvalidHashAlgorithmException extends RuntimeException {

    /**
     * Powołuje do życia nowy wyjątek InvalidHashAlgorithmException będący
     * wyjątkiem rodzaju unchecked.
     */
    public InvalidHashAlgorithmException() {
        super();
    }
}
