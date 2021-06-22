import axios from "axios";
import Cookies from "js-cookie";
import {AppointmentSlot} from "../AppointmentSlot";

export async function makeAppointmentSlotsListRequest() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getData(token);
    let appointments = []
    for (const i in data) {
        appointments.push(new AppointmentSlot(data[i].id, data[i].date, data[i].doctor.firstName + " "+ data[i].doctor.lastName))
    }
    return appointments
}

async function getData(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/available", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}

