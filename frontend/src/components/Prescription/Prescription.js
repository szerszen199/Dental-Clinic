export class Prescription {
    constructor(expiration, patientName, doctorName, medications) {
        this.expiration = expiration;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.medications = medications
    }
}