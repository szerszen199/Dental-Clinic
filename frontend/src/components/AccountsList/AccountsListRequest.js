import axios from "axios";
import {Account} from "./Account";
import Cookies from "js-cookie";

// TODO usuwanie tego gdy minie określony czas czytaj przedawni się

export async function makeAccountsListRequest() {

    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let recivedData = await axios.get(process.env.REACT_APP_BACKEND_URL + "account/accounts", {
        headers: {"Authorization": "Bearer " + token}
    })
    let data = recivedData.data
    let accounts = []
    for (const i in recivedData.data) {
        accounts.push(new Account(data[i].firstName + " " + data[i].lastName, data[i].email, data[i].login))
    }
    return accounts
}

