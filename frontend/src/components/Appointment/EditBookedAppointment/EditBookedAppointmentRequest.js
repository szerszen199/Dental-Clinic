import axios from "axios";
import Cookies from "js-cookie";
import successAlerts from "../../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";


export function editBookedAppointmentRequest(appId, patientLogin, appointmentDate, version, etag, t) {

    axios.post(process.env.REACT_APP_BACKEND_URL + "appointment/edit", {
        id: appId,
        patientLogin: patientLogin,
        appointmentDate: appointmentDate,
        version: version

    }, {
        headers: {
            'Authorization': "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME),
            'Content-Type': 'application/json',
            'If-Match': etag
        }
    }).then((response) => {
        successAlerts(t(response.data.message, response.status)).then(() => {
            window.location.hash = "#/my-appointments";
        })
    }).catch((response) => {
        if (response.response) {
            errorAlerts(t(response.response.data.message), response.response.status.toString(10));
        }
    });
}