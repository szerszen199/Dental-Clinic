import axios from "axios";
import React from "react";
import {Alert} from "react-bootstrap";

export function registrationRequest(login, email, password, firstName, lastName, phoneNumber, pesel, language) {

    axios.post(process.env.REACT_APP_BACKEND_URL + "account/create", {
            login: login,
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel,
            language: language
        }
    )
        .then(function (response) {
            console.log(JSON.stringify(response.data));
            window.location = "/home";
        })
        .catch(function (error) {
            console.log(JSON.stringify(error));
        });
}