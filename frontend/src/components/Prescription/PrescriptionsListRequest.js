import axios from "axios";
import {Prescription} from "./Prescription";
import Cookies from "js-cookie";

export async function makePrescriptionsListRequest(isPatient) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data
    if(isPatient === true){
        data = await getDataPatient(token);
    } else {
        data = await getDataDoctor(token);
    }

    let prescriptions = []
    for (const i in data) {
        prescriptions.push(new Prescription(data[i].expiration, data[i].patientFirstname + " " + data[i].patientLastname,
            data[i].doctorFirstname + " " + data[i].doctorLastname, data[i].medications))
    }
    return prescriptions
}

async function getDataPatient(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "prescription/prescriptions/patient", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}

async function getDataDoctor(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "prescription/prescriptions/doctor", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}

