import React from "react";
import "./Receptionist.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {logout} from "../../components/Login/Logout";


function Receptionist() {
    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title="Appointments" id="navbarScrollingDropdown">
                <Nav.Link to="/my-appointments">Appointments</Nav.Link>
            </NavDropdown>
            <Nav.Link to="/accounts">Accounts</Nav.Link>
            <NavDropdown title="My Account" id="navbarScrollingDropdown">
                <Nav.Link to="/account">Edit My Account</Nav.Link>
                <span onClick={logout} style={{paddingLeft: "8px", color: "rgb(127, 127, 127)"}}>Log out</span>
            </NavDropdown>
        </Nav>
    );
}


export default Receptionist;
