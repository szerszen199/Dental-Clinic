import axios from "axios";
import React from "react";
import {JWTTokenStorageName, userRolesStorageName} from "./LoginRequest";


export function logout() {
    localStorage.setItem(JWTTokenStorageName, null);
    localStorage.setItem(userRolesStorageName, null);
    // TODO: To redirect po wylogowaniu zalogowaniu, nie podoba mi się, nie korzysta z routera ale inaczej mi nie chce narazie pojsc.
    window.location = "/login";
}