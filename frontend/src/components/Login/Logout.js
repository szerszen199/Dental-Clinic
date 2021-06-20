import Cookies from 'js-cookie'


export function logout() {
    Cookies.remove(process.env.REACT_APP_JWT_TOKEN_COOKIE_NAME, {secure: true, sameSite: 'none'});
    Cookies.remove(process.env.REACT_APP_ROLES_COOKIE_NAME, {secure: true, sameSite: 'none'});
    Cookies.remove(process.env.REACT_APP_ACTIVE_ROLE_COOKIE_NAME, {secure: true, sameSite: 'none'});
    Cookies.remove(process.env.REACT_APP_LOGIN_COOKIE, {secure: true, sameSite: 'none'})
    Cookies.remove(process.env.REACT_APP_DARK_MODE_COOKIE, {secure: true, sameSite: 'none'})
    Cookies.remove(process.env.REACT_APP_LANGUAGE_COOKIE, {secure: true, sameSite: 'none'})
    localStorage.setItem(process.env.REACT_APP_JWT_REFRESH_TOKEN_STORAGE_NAME, null);
    window.location.hash = "#/guest-home";
    window.location.reload();
}
