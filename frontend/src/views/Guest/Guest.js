import React from "react";
import "./Guest.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";

function Guest() {
    return (
        <Nav activeKey={window.location.pathname}>
            <LinkContainer to="/register">
                <Nav.Link>Register</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/login">
                <Nav.Link>Login</Nav.Link>
            </LinkContainer>
        </Nav>

    );
}


export default Guest;
