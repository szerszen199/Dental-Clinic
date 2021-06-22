export class Appointment {
    constructor(id, doctorLogin, patientLogin, date, version, doctorName, patientName, etag) {
        this.id = id;
        this.patientLogin = patientLogin;
        this.doctorLogin = doctorLogin;
        this.date = date;
        this.version = version;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.etag = etag;
    }
}
