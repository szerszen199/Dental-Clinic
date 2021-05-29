import axios from "axios";

export function makeResetPasswordRequest(login) {
    axios.put(process.env.REACT_APP_BACKEND_URL + "account/reset-password", {
        login: login,
    }).then(function (response) {
        console.log(JSON.stringify(response.data));
        window.location = "/home";
    }).catch(function (error) {
        console.log(error);
    });

}

