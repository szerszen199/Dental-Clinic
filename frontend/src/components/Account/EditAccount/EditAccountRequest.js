import axios from "axios";
import Cookies from "js-cookie";

export function editAccountRequest(email, firstName, lastName, phoneNumber, pesel,version, etag){
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let config = {
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

    axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });
}
