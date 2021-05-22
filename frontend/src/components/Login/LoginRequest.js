import axios from "axios";
import Cookies from 'js-cookie'
import {jwtCookieExpirationTime} from "../../views/MainView/MainView";

// TODO usuwanie tego gdy minie określony czas czytaj przedawni się

export function makeLoginRequest(login, password) {
    axios.post(process.env.REACT_APP_BACKEND_URL + "auth/login", {
        username: login,
        password: password
    }).then((response) => {
        // TODO: Czas expieracji.
        Cookies.set(process.env.JWTTokenCookieName, response.data.authJwtToken.token, { expires: jwtCookieExpirationTime});
        Cookies.set(process.env.RolesCookieName, response.data.roles, {expires: jwtCookieExpirationTime});
        localStorage.setItem(process.env.JWTRefreshTokenStorageName, response.data.refreshJwtToken.token);
        // TODO: To redirect po poprawnym zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
        window.location = "/home";
    }).catch((response) => {
        // todo Wyświetlić odpowiedni komunikat
    })
}

