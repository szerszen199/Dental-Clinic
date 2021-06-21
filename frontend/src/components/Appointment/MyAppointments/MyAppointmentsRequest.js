import axios from "axios";
import {Appointment} from "./Appointment";
import Cookies from "js-cookie";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import moment from "moment";

export async function makeMyAppointmentsListRequest(t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let currentRole = Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME)
    let data = await getData(token, currentRole, t);
    let appointments = [];
    data.sort((a, b) => (a.date > b.date) ? 1 : -1)
    for (const i of data) {
        appointments.push(new Appointment(i.id, i.doctorLogin, i.patientLogin, moment(i.date).format('DD.MM.YYYY HH:mm'),
            i.version, i.doctorFirstName + " " + i.doctorLastName, i.patientFirstName + " " + i.patientLastName, i.etag));
    }
    return appointments;
}

async function getData(token, currentRole, t) {
    let endpoint = "";
    if (currentRole === process.env.REACT_APP_ROLE_RECEPTIONIST) {
        endpoint = "appointment/all-scheduled-appointments";
    } else if (currentRole === process.env.REACT_APP_ROLE_DOCTOR) {
        endpoint = "appointment/all-scheduled-appointments-by-doctor";
    } else if (currentRole === process.env.REACT_APP_ROLE_PATIENT) {
        endpoint = "appointment/all-scheduled-appointments-by-patient";
    } else {
        return;
    }

    return axios.get(process.env.REACT_APP_BACKEND_URL + endpoint, {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data.scheduledAppointmentResponseDTOS
    ).catch((response) => {
        if (response.response) {
            errorAlerts(t(response.response.data.message), response.response.status.toString(10));
        }
    });
}

