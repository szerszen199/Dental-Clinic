import axios from "axios";
import Cookies from "js-cookie";

export function darkModeRequest(isDarkMode){
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let config = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL + "account/dark-mode",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        data: {
            darkMode: isDarkMode
        }
    };

    axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });
}