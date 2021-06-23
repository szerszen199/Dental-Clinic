import Cookies from "js-cookie";
import axios from "axios";
import {Doctor} from './Doctor';

export async function makeDoctorsListRequest(t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getData(token, t);
    let doctors = []
    for (const i in data) {
        doctors.push(new Doctor(data[i].login, data[i].firstName + " " + data[i].lastName, data[i].avgRate, data[i].ratesCounter));
    }
    return doctors;
}

async function getData(token, t) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "appointment/doctors", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
    .catch(response => {
        if (response.response) {
            errorAlerts(t(response.response.data.message), response.response.status.toString(10));
        }
    });
}
