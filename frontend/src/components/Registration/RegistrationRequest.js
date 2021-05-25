import axios from "axios";

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
        .then((response) => {
            console.log(JSON.stringify(response.data));
            console.log(JSON.stringify(response.statusText));
            window.location = "/home";
        })
        .catch((error) => {
            console.log(JSON.stringify(error));
        });
}