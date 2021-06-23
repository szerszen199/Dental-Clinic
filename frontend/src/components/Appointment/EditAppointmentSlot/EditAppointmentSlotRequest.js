import axios from "axios";
import successAlerts from "../../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import Cookies from "js-cookie";


export function editAppointmentSlotRequest(appId, doctorLogin, appointmentDate, version, etag, t) {

    axios.put(process.env.REACT_APP_BACKEND_URL + "appointment/edit-slot", {
        id: appId,
        doctorLogin: doctorLogin,
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
            window.location.hash = "#/plan-appointment-receptionist";
        })
    }).catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
