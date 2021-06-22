export class PrescriptionEntry{

    constructor(prescriptionId, expiration, patientFirstname, patientLastname, doctorFirstname, doctorLastname, creationDateTime, medications) {
        this.prescriptionId = prescriptionId;
        this.expiration = expiration;
        this.patientFirstname = patientFirstname;
        this.patientLastname = patientLastname;
        this.doctorFirstname = doctorFirstname;
        this.doctorLastname = doctorLastname;
        this.creationDateTime = creationDateTime;
        this.medications = medications;
    }

    // private final Long prescriptionId;
    // private final LocalDateTime expiration;
    // private final String patientFirstname;
    // private final String patientLastname;
    // private final String doctorFirstname;
    // private final String doctorLastname;
    // private final LocalDateTime creationDateTime;
    // private final String medications;
}