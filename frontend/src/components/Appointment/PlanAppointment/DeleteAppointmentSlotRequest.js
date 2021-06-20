import Cookies from "js-cookie";
import axios from "axios";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import successAlertsWithRefresh from "../../Alerts/SuccessAlerts/SuccessAlertsWithRefresh";

export function deleteAppointmentSlotRequest(id, t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let configDeleteAppointmentSlot = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "appointment/slot/delete",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        data: {
            id: id
        }
    };

    axios(configDeleteAppointmentSlot)
        .then(response => {
            successAlertsWithRefresh(t(response.data.message), response.status.toString(10));
        })
        .catch(response => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
