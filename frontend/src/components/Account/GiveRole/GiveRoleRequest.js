import axios from "axios";
import Cookies from "js-cookie";


export function giveRoleRequest(login, level, refresh, render){
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
            refresh();
            render();
        })
        .catch(function (error) {
            console.log(error);
        });
}
