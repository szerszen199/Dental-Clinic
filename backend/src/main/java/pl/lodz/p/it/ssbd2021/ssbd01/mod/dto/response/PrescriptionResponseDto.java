package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response;

import java.time.LocalDateTime;

public class PrescriptionResponseDto {

    private final Long prescriptionId;
    private final LocalDateTime expiration;
    private final String patientFirstname;
    private final String patientLastname;
    private final String doctorLogin;
    private final String doctorFirstname;
    private final String doctorLastname;
    private final LocalDateTime creationDateTime;
    private final String medications;

    /**
     * Instantiates a new Prescription response dto.
     *
     * @param prescriptionId   the prescription id
     * @param expiration       the expiration
     * @param patientFirstname the patient firstname
     * @param patientLastname  the patient lastname
     * @param doctorLogin      login doktora
     * @param doctorFirstname  the doctor firstname
     * @param doctorLastname   the doctor lastname
     * @param creationDateTime the creation date time
     * @param medications      the medications
     */
    public PrescriptionResponseDto(Long prescriptionId, LocalDateTime expiration, String patientFirstname, String patientLastname,
                                   String doctorLogin, String doctorFirstname, String doctorLastname, LocalDateTime creationDateTime, String medications) {
        this.prescriptionId = prescriptionId;
        this.expiration = expiration;
        this.patientFirstname = patientFirstname;
        this.patientLastname = patientLastname;
        this.doctorLogin = doctorLogin;
        this.doctorFirstname = doctorFirstname;
        this.doctorLastname = doctorLastname;
        this.creationDateTime = creationDateTime;
        this.medications = medications;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public String getPatientFirstname() {
        return patientFirstname;
    }

    public String getPatientLastname() {
        return patientLastname;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public String getDoctorFirstname() {
        return doctorFirstname;
    }

    public String getDoctorLastname() {
        return doctorLastname;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public String getMedications() {
        return medications;
    }
}
