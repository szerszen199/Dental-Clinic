import axios from "axios";
import {Patient} from "./Patient";
import Cookies from "js-cookie";

export async function makePatientsListRequest() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getData(token);
    let patients = []
    for (const i in data) {
        patients.push(new Patient(data[i].firstName + " " + data[i].lastName, data[i].email, data[i].login, data[i].phoneNumber, data[i].pesel))
    }
    return patients
}

async function getData(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/patients", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}

