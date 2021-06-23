package pl.lodz.p.it.ssbd2021.ssbd01.mow.dto.response;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Typ DoctorAndRateResponseDTO - klasa DTO reprezentująca informację o lekarzu i jego ocenie.
 */
public class DoctorAndRateResponseDTO {
    
    @NotNull
    private String login;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @DecimalMin(value = "0.0")
    private double avgRate;

    @NotNull
    @Min(value = 0)
    private int ratesCounter;

    /**
     * Tworzy nową instancję klasy DoctorAndRateResponseDTO.
     *
     * @param login     login lekarza
     * @param firstName imię lekarza
     * @param lastName  nazwisko lekarza
     * @param avgRate   średnia ocena
     * @param ratesCounter licznik ocen
     */
    public DoctorAndRateResponseDTO(String login, String firstName, String lastName, double avgRate, int ratesCounter) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avgRate = avgRate;
        this.ratesCounter = ratesCounter;
    }

    /**
     * Pobiera pole firstName.
     *
     * @return imię
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Pobiera pole lastName.
     *
     * @return nazwisko
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Pobiera pole avgRate.
     *
     * @return średnia ocen
     */
    public double getAvgRate() {
        return avgRate;
    }

    /**
     * Pobiera pole login.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Pobiera pole ratesCounter.
     *
     * @return licznik ocen
     */
    public int getRatesCounter() {
        return ratesCounter;
    }
}
