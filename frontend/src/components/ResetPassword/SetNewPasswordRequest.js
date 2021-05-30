import axios from "axios";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import successAlerts from "../Alerts/SuccessAlerts/SuccessAlerts";

export function setNewPasswordRequest(token, firstPassword, secondPassword, t){

    let config = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL + "account/set-new-password",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            confirmToken: token,
            firstPassword: firstPassword,
            secondPassword: secondPassword
        }
    };

    axios(config)
        .then((response) => {
            successAlerts(t(response.data.message, response.status)).then(() => {
                window.location = "/home";
            })
        })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
