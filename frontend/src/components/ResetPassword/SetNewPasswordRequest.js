import axios from "axios";

export function setNewPasswordRequest(token, firstPassword, secondPassword){

    let config = {
        method: 'put',
        url: process.env.REACT_APP_BACKEND_URL + "account/set-new-password",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            confirmToken: token,
            firstPassword: firstPassword,
            secondPassword: secondPassword
        }
    };

    axios(config)
        .then(function (response) {
            console.log(JSON.stringify(response.data));
        })
        .catch(function (error) {
            console.log(error);
        });

    window.location = "/home";
}
