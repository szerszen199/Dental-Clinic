import axios from "axios";
import Cookies from 'js-cookie'
import findDefaultRole from "../../roles/findDefaultRole";
import {jwtCookieExpirationTime} from "../../views/MainView/MainView";

// TODO usuwanie tego gdy minie określony czas czytaj przedawni się

export function makeLoginRequest(login, password) {
    return axios.post(process.env.REACT_APP_BACKEND_URL + "auth/login", {
        username: login,
        password: password
    }).then((response) => {
        // TODO: Czas expieracji.
        Cookies.set(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME, response.data.authJwtToken.token, {expires: jwtCookieExpirationTime});
        Cookies.set(process.env.REACT_APP_ROLES_COOKIE_NAME, response.data.roles, {expires: jwtCookieExpirationTime});
        Cookies.set(process.env.REACT_APP_LOGIN_COOKIE, response.data.username,{expires: jwtCookieExpirationTime})
        Cookies.set(process.env.REACT_APP_LANGUAGE_COOKIE, response.data.userInfoResponseDTO.language, {expires: jwtCookieExpirationTime})
        Cookies.set(process.env.REACT_APP_DARK_MODE_COOKIE, response.data.userInfoResponseDTO.darkMode, {expires: jwtCookieExpirationTime})
        if(Cookies.get(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME) == null) {
            Cookies.set(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, findDefaultRole(response.data.roles), {expires: jwtCookieExpirationTime});
        }
        localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, response.data.refreshJwtToken.token);
        // TODO: To redirect po poprawnym zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
        window.location = "/home";
        return true;
    }).catch((response) => {
        // todo Wyświetlić odpowiedni komunikat
    })
}
