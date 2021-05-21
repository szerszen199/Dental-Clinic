import {JWTRefreshTokenStorageName, JWTTokenCookieName, RolesCookieName} from "./LoginRequest";
import Cookies from 'js-cookie'


export function logout() {
    Cookies.remove(JWTTokenCookieName);
    Cookies.remove(RolesCookieName);
    localStorage.setItem(JWTRefreshTokenStorageName, null);
    // TODO: To redirect po wylogowaniu zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
    window.location = "/login";
}
