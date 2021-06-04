import axios from "axios";
import Cookies from "js-cookie";

export function changeRoleRequest(level) {
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);

    let changeLevelConfig = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "auth/change-level/"+level,
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
    };


    axios(changeLevelConfig)
        .then((response) => {
            return true;
        }
        )
        .catch((response) => {
            if (response.response) {
            }
            return false;
        });
    return true;
}