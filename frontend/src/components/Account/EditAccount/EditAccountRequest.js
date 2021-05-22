import axios from "axios";
import Cookies from "js-cookie";
import {JWTTokenCookieName} from "../../Login/LoginRequest";

export function editAccountRequest(email, firstName, lastName, phoneNumber, pesel){
    let token = Cookies.get(JWTTokenCookieName);
    let config = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "account/edit",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        data: {
            email: email,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel
        }
    };

    axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });
}
