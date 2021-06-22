import axios from "axios";
import {Account} from "./Account";
import Cookies from "js-cookie";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";

export async function makeAccountsListRequest(t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let data = await getData(token, t);
    let accounts = []
    for (const i in data) {
        accounts.push(new Account(data[i].firstName + " " + data[i].lastName, data[i].email, data[i].login))
    }
    return accounts
}

async function getData(token, t) {
    return axios.get(process.env.REACT_APP_BACKEND_URL + "account/accounts", {
        headers: {"Authorization": "Bearer " + token}
    }).then(response => response.data)
    .catch((response) => {
        if (response.response) {
            errorAlerts(t(response.response.data.message), response.response.status.toString(10));
        }
    });
}

