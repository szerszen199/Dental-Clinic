export class PrescriptionEntry{

    constructor(prescriptionId, expiration, patientFirstname, patientLastname, doctorLogin, doctorFirstname, doctorLastname, creationDateTime, medications) {
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
}