package pl.lodz.p.it.ssbd2021.ssbd01.mod.dto.response;

import java.time.LocalDateTime;

public class PrescriptionResponseDto {

    private final Long prescriptionId;
    private final LocalDateTime expiration;
    private final String patientFirstname;
    private final String patientLastname;
    private final String doctorFirstname;
    private final String doctorLastname;
    private final LocalDateTime creationDateTime;
    private final byte[] medications;

    public PrescriptionResponseDto(Long prescriptionId, LocalDateTime expiration, String patientFirstname, String patientLastname, String doctorFirstname, String doctorLastname, LocalDateTime creationDateTime, byte[] medications) {
        this.prescriptionId = prescriptionId;
        this.expiration = expiration;
        this.patientFirstname = patientFirstname;
        this.patientLastname = patientLastname;
        this.doctorFirstname = doctorFirstname;
        this.doctorLastname = doctorLastname;
        this.creationDateTime = creationDateTime;
        this.medications = medications;
    }
}
