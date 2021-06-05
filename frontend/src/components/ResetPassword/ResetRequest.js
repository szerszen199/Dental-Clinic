import axios from "axios";
import successAlerts from "../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";

export function makeResetPasswordRequest(login, t) {
    axios.put(process.env.REACT_APP_BACKEND_URL + "account/reset-password", {
        login: login,
    }).then((response) => {
        successAlerts(t(response.data.message, response.status)).then(() => {
            window.location.hash = "#/login";
        })

    })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
    // window.location = "/home";
}

