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
}