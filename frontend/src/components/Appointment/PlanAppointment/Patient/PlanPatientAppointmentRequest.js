import axios from "axios";
import errorAlerts from "../../../Alerts/ErrorAlerts/ErrorAlerts";
import successAlerts from "../../../Alerts/SuccessAlerts/SuccessAlerts";
import Cookies from "js-cookie";

export function planPatientAppointmentRequest(appointmentId, t) {

    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let reserveForOwnAccount = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL+ "reservation/reserve/self",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        data: {
            appointmentId: appointmentId
        }
    };


    axios(reserveForOwnAccount)
        .then((response) => {
            console.log("halo")
            successAlerts(t(response.data.message, response.status)).then(() => {
            })
        })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });}



