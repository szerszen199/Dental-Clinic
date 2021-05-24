import axios from "axios";

export function makeResetPasswordRequest(login) {
    axios.put(process.env.REACT_APP_BACKEND_URL + "account/reset-password", {
        login: login,
    })
    window.location = "/home";
}

