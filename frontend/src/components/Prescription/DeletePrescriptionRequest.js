import axios from "axios";
import Cookies from "js-cookie";
import successAlerts from "../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";
import successAlertsWithRefresh from "../Alerts/SuccessAlerts/SuccessAlertsWithRefresh";

export function makeDeletePrescriptionRequest(id, t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let configDeletePrescription = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "prescription/delete",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        data: {
            id: id
        }
    };

    axios(configDeletePrescription)
        .then(function (response) {
            successAlertsWithRefresh(t(response.data.message, response.status.toString(10)));
        })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}