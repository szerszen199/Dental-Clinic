import axios from "axios";
import Cookies from 'js-cookie'
import findDefaultRole from "../../roles/findDefaultRole";
import {jwtCookieExpirationTime} from "../../views/MainView/MainView";
import "../Alerts/ErrorAlerts/ErrorAlerts";
import errorAlerts from "../Alerts/ErrorAlerts/ErrorAlerts";

export function makeLoginRequest(login, password, t) {
    axios.post(process.env.REACT_APP_BACKEND_URL + "auth/login", {
        username: login,
        password: password
    }).then((response) => {
        if(response.status === 210){
            window.location = "#/new-password-admin/" + response.data.token;
            window.location.reload();
        } else {
            Cookies.set(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME, response.data.authJwtToken.token, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
            Cookies.set(process.env.REACT_APP_ROLES_COOKIE_NAME, response.data.roles, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
            Cookies.set(process.env.REACT_APP_LOGIN_COOKIE, response.data.username, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
            Cookies.set(process.env.REACT_APP_LANGUAGE_COOKIE, response.data.userInfoResponseDTO.language, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
            Cookies.set(process.env.REACT_APP_DARK_MODE_COOKIE, response.data.userInfoResponseDTO.darkMode, {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
            if (Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) == null) {
                Cookies.set(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, findDefaultRole(response.data.roles), {expires: jwtCookieExpirationTime, secure: true, sameSite: 'none'});
            }
            localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, response.data.refreshJwtToken.token);
            window.location.hash = "#/home";
            window.location.reload();
        }
    }).catch((response) => {
        if (response.response) {
            errorAlerts(t(response.response.data.message), response.response.status.toString(10));
        }
    })
}
