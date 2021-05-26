import axios from "axios";
import Cookies from "js-cookie";

export function editPasswordRequest(oldPassword, firstPassword, secondPassword){
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let config = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL + "account/new-password",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        data: {
            oldPassword: oldPassword,
            firstPassword: firstPassword,
            secondPassword: secondPassword
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
