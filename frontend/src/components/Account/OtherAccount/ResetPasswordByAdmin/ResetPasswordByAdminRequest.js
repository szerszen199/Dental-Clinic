import axios from "axios";
import errorAlerts from "../../../Alerts/ErrorAlerts/ErrorAlerts";
import Cookies from "js-cookie";
import successAlertsWithRefresh from "../../../Alerts/SuccessAlerts/SuccessAlertsWithRefresh";

export function makeResetPasswordByAdminRequest(login, t) {
    axios.put(process.env.REACT_APP_BACKEND_URL + "account/reset-other-password", {
        login: login,
    }, {
        headers: {
            Authorization: "Bearer " + Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME)
        }
    }).then(function (response) {
        successAlertsWithRefresh(t(response.data.message), response.status.toString(10)).then(() => {
        })
    }).catch((response) => {
        if (response.response) {
            errorAlerts(t(response.response.data.message), response.response.status.toString(10));
        }
    });
}