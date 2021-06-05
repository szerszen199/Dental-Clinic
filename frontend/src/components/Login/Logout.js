import Cookies from 'js-cookie'


export function logout() {
    Cookies.remove(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME);
    Cookies.remove(process.env.REACT_APP_ROLES_COOKIE_NAME);
    Cookies.remove(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME);
    Cookies.remove(process.env.REACT_APP_LOGIN_COOKIE)
    Cookies.remove(process.env.REACT_APP_DARK_MODE_COOKIE)
    localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, null);
    window.location = "/login";
}
