import React from "react";
import "./Admin.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";


function Admin() {
    return (
        <Nav activeKey={window.location.pathname}>
            <LinkContainer to="/account">
                <Nav.Link>My Account</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/accounts">
                <Nav.Link>User's Accounts</Nav.Link>
            </LinkContainer>
        </Nav>
    );
}

export default Admin;
