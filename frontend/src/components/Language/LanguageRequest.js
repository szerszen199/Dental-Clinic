import axios from "axios";
import Cookies from "js-cookie";

export function languageRequest(language){
    let token = Cookies.get(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    let config = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL + "account/language",
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        data: {
            language: language
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

export function getBrowserLanguage() {
    let language = window.navigator.language.split('-')[0];
    return String(language.toUpperCase());
}