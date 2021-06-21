import axios from "axios";
import {Account} from "./Account";
import Cookies from "js-cookie";

export async function makeAccountsListRequest() {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getData(token);
    data = data.patients;
    let accounts = [];
    for (const i in data) {
        accounts.push(new Account(data[i].name + " " + data[i].lastName, data[i].email, data[i].login));
    }
    return accounts;
}

async function getData(token) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "account/patients", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
}

