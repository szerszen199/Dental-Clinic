import axios from "axios";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import successAlerts from "../Alerts/SuccessAlerts/SuccessAlerts";

export function registrationRequest(login, email, password, firstName, lastName, phoneNumber, pesel, language, t) {

    axios.post(process.env.REACT_APP_BACKEND_URL + "account/create", {
            login: login,
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel,
            language: language
        }
    ).then((response) => {
        successAlerts(t(response.data.message, response.status)).then(() => {
            window.location.hash = "#/login";
        })
    })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
