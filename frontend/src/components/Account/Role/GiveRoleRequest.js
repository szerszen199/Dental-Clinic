import axios from "axios";
import Cookies from "js-cookie";
import successAlerts from "../../Alerts/SuccessAlerts/SuccessAlerts";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";


export function giveRoleRequest(login, level, refresh, render, t){
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let configOwnAccount = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL + "account/addLevelByLogin",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        data: {
            login: login,
            level: level
        }
    };

    axios(configOwnAccount)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
            successAlerts(t(response.data.message, response.status)).then(() => {})
            refresh();
            render();
        })
        .catch((response) => {
            console.log(response);
            if (response.response) {
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
            }
        });
}
