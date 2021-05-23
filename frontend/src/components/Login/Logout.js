import Cookies from 'js-cookie'


export function logout() {
    Cookies.remove(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    Cookies.remove(process.env.REACT_APP_ROLES_COOKIE_NAME);
    localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, null);
    // TODO: To redirect po wylogowaniu zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
    window.location = "/login";
}
