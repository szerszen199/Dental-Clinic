import axios from "axios";
import Cookies from "js-cookie";
import errorAlerts from "../../Alerts/ErrorAlerts/ErrorAlerts";

export async function changeRoleRequest(level, t) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let changeLevelConfig = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "auth/change-level/" + level,
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
    };


    return await axios(changeLevelConfig)
        .then(() => {
                return true;
            }
        )
        .catch((response) => {
            if (response.response) {
                console.log(response)
                errorAlerts(t(response.response.data.message), response.response.status.toString(10));
                return false
            }
        })
}