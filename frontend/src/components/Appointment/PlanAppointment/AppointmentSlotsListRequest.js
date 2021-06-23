import axios from "axios";
import Cookies from "js-cookie";
import {AppointmentSlot} from "../AppointmentSlot";
import moment from "moment";

export async function makeAppointmentSlotsListRequest() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getData(token);
    let appointments = []
    for (const i in data) {
        appointments.push(new AppointmentSlot(data[i].id, moment(data[i].date).format("DD.MM.YYYY HH:mm"), data[i].doctor))
    }
    return appointments
}

export async function makeDoctorAppointmentSlotsListRequest() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getDoctorData(token);
    let appointments = []
    for (const i in data) {
        appointments.push(new AppointmentSlot(data[i].id, moment(data[i].date).format("DD.MM.YYYY HH:mm"), data[i].doctor.firstName + " "+ data[i].doctor.lastName))
    }
    return appointments
}

async function getData(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/available", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}

async function getDoctorData(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/available-own", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}



