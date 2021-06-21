import axios from "axios";
import {Appointment} from "./Appointment";
import Cookies from "js-cookie";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import moment from "moment";

const roleDoctorName = process.env.REACT_APP_ROLE_DOCTOR
const roleReceptionistName = process.env.REACT_APP_ROLE_RECEPTIONIST
const rolePatientName = process.env.REACT_APP_ROLE_PATIENT

export async function makeMyAppointmentsListRequest(t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let currentRole = Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME)
    let data = await getData(token, currentRole, t);
    let appointments = [];
    data.sort((a, b) => (a.date > b.date) ? 1 : -1)
    for (const i in data) {
        appointments.push(new Appointment(data[i].id, data[i].doctorLogin, data[i].patientLogin, moment(data[i].date).format('DD.MM.YYYY HH:mm'),
            data[i].version, data[i].doctorFirstName + " " + data[i].doctorLastName,
            data[i].patientFirstName + " " + data[i].patientLastName, data[i].etag));
    }
    return appointments;
}

async function getData(token, currentRole, t) {
    if (currentRole === roleReceptionistName) {
        return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/all-scheduled-appointments", {
            headers: {"Authorization": "Bearer " + token}
        }).then(response => response.data.scheduledAppointmentResponseDTOS
        ).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    }
    if (currentRole === roleDoctorName) {
        return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/all-scheduled-appointments-by-doctor", {
            headers: {"Authorization": "Bearer " + token}
        }).then(response => response.data.scheduledAppointmentResponseDTOS
        ).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    }
    if (currentRole === rolePatientName) {
        return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/all-scheduled-appointments-by-patient", {
            headers: {"Authorization": "Bearer " + token}
        }).then(response => response.data.scheduledAppointmentResponseDTOS
        ).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    }
}

