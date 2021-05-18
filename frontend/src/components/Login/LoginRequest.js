import React, {useState} from "react";
import axios from "axios";


export let userRoles = [];
export let JWTToken = "";

export function makeLoginRequest(login, password) {
    console.log(process.env.REACT_APP_BACKEND_URL + "login")
    axios.post(process.env.REACT_APP_BACKEND_URL + "login", {
        username : login,
        password: password
    }).then(function (response) {
        console.log(response)
        // JWTToken = response.data.token;
        // userRoles = response.data.roles;
    })
}

class ReadinessComponent extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    render() {
        console.log(this.state.ready)
        return (
            <span/>
        )
    }
}