import React from "react";
import axios from "axios";
import {useHistory} from "react-router-dom";

// TODO usuwanie tego gdy minie określony czas czytaj przedawni się


export const JWTTokenStorageName = 'JWTToken'
export const userRolesStorageName = 'userRoles'

export function makeLoginRequest(login, password) {
    axios.post(process.env.REACT_APP_BACKEND_URL + "login", {
        username: login,
        password: password
    }).then ((response) => {
        localStorage.setItem(userRolesStorageName, response.data.roles);
        localStorage.setItem(JWTTokenStorageName, response.data.token);
        // TODO: To redirect po poprawnym zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
        window.location = "/home";
    }).catch((response) => {
        // todo Wyświetlić odpowiedni komunikat
    })


}

