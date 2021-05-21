import axios from "axios";
import {Account} from "./Account";

// TODO usuwanie tego gdy minie określony czas czytaj przedawni się


export const JWTTokenStorageName = 'JWTToken'
export const userRolesStorageName = 'userRoles'

export async function makeAccountsListRequest() {

    let recivedData = await axios.get(process.env.REACT_APP_BACKEND_URL + "account/accounts", {
        headers: {"Authorization": "Bearer " + localStorage.getItem(JWTTokenStorageName)}
    })
    let data = recivedData.data
    let accounts = []
    for (const i in recivedData.data) {
        accounts.push(new Account(data[i].firstName + " " + data[i].lastName, data[i].email,data[i].login))
    }
    return accounts
}

