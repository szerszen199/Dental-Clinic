import axios from "axios";
import Cookies from 'js-cookie'

// TODO usuwanie tego gdy minie określony czas czytaj przedawni się

export function makeLoginRequest(login, password) {
    axios.post(process.env.REACT_APP_BACKEND_URL + "auth/login", {
        username: login,
        password: password
    }).then((response) => {
        // TODO: Czas expieracji.
        Cookies.set(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME, response.data.authJwtToken.token, { expires: process.env.jwtCookieExpirationTime});
        Cookies.set(process.env.REACT_APP_ROLES_COOKIE_NAME, response.data.roles, {expires: process.env.jwtCookieExpirationTime});
        localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, response.data.refreshJwtToken.token);
        // TODO: To redirect po poprawnym zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
        window.location = "/home";
    }).catch((response) => {
        // todo Wyświetlić odpowiedni komunikat
    })
}

