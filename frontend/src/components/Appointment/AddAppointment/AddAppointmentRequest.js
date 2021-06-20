import axios from "axios";
import successAlerts from "../../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";
import Cookies from "js-cookie";


export function addAppointmentRequest(doctorLogin, appointmentDate, t) {

    axios.post(process.env.REACT_APP_BACKEND_URL + "appointment/create", {
            doctorLogin: doctorLogin,
            appointmentDate: appointmentDate

        }, {
        headers: {
            Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
        }
    }).then((response) => {
        successAlerts(t(response.data.message, response.status)).then(() => {
            window.location.hash = "#/plan-appointment";
        })
    })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
