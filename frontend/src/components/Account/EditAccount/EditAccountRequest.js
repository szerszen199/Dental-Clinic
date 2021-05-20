import axios from "axios";

export function editAccountRequest(login, email, firstName, lastName, phoneNumber, pesel){
    let config = {
        method: 'post',
        url: process.env.REACT_APP_BACKEND_URL + "account/edit",
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem("JWTToken"),
            'Content-Type': 'application/json'
        },
        data: {
            login: login,
            email: email,
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phoneNumber,
            pesel: pesel
        }
    };

    axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });
}
