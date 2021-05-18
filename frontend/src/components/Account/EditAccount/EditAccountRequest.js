import axios from "axios";

export const JWTTokenStorageName = 'JWTToken'
export const userRolesStorageName = 'userRoles'

// export function makeEditRequest(login, password) {
//     axios.post(process.env.REACT_APP_BACKEND_URL + "login", {
//         username: login,
//         password: password
//     }).then((response) => {
//         localStorage.setItem(userRolesStorageName, response.data.roles);
//         localStorage.setItem(JWTTokenStorageName, response.data.token);
//         // TODO: To redirect po poprawnym zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
//         window.location = "/home";
//     }).catch((response) => {
//         // todo Wyświetlić odpowiedni komunikat
//     })
// }

export async function makeGetEditRequest() {
    // let r = "";
    return await axios.get(process.env.REACT_APP_BACKEND_URL + "account/info", {
        headers: {
            Authorization: "Bearer " + localStorage.getItem(JWTTokenStorageName)
        }
    }).then((response) => {
        // console.log(response.data)
        return response.data;
        // console.log(r)
        // return r;
    }).catch((response) => {
        // todo Wyświetlić odpowiedni komunikat
    });
}