import axios from "axios";
import Cookies from "js-cookie";
import successAlertsWithRefresh from "../../Alerts/SuccessAlerts/SuccessAlertsWithRefresh";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";

export function editAccountRequest(email, firstName, lastName, phoneNumber, pesel, version, etag, login, t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let configOwnAccount = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "account/edit",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
            "If-Match": etag
        },
        data: {
            email: email,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel,
            version: version
        }
    };

    let configOtherAccount = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "account/edit-other",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
            "If-Match": etag
        },
        data: {
            login: login,
            email: email,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel,
            version: version
        }
    };

    let axiosConfig
    if (login === undefined) {
        axiosConfig = configOwnAccount
    } else {
        axiosConfig = configOtherAccount
    }

    axios(axiosConfig)
        .then((response) => {
            successAlertsWithRefresh(t(response.data.message, response.status)).then(() => {
            })
        })
        .catch((response) => {
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
