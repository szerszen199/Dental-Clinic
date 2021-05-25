import axios from "axios";
import Cookies from "js-cookie";

export function editAccountRequest(email, firstName, lastName, phoneNumber, pesel,version, etag, login){
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let configOwnAccount = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "account/edit",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
            "If-Match": etag
        },
        data: {
            email: email,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel,
            version: version
        }
    };

    let configOtherAccount = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "account/edit-other",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
            "If-Match": etag
        },
        data: {
            login: login,
            email: email,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel,
            version: version
        }
    };

    let axiosConfig
    if (login === undefined) {
        axiosConfig = configOwnAccount
    } else {
        axiosConfig = configOtherAccount
    }

    axios(axiosConfig)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });
}
