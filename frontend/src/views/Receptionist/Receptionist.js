import React from "react";
import "./Receptionist.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";



function Receptionist() {
    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title="Appointments" id="navbarScrollingDropdown">
                <NavDropdown.Item>
                    <LinkContainer to="/my-appointments">
                        <Nav.Link>Appointments</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
            </NavDropdown>
            <LinkContainer to="/accounts">
                <Nav.Link>Accounts</Nav.Link>
            </LinkContainer>
            <NavDropdown title="My Account" id="navbarScrollingDropdown">
                <NavDropdown.Item>
                    <LinkContainer to="/account">
                        <Nav.Link>Edit My Account</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
                <NavDropdown.Item>
                    <span onClick={logout} style={{paddingLeft: "8px", color:"rgb(127, 127, 127)"}}>Log out</span>
                </NavDropdown.Item>
            </NavDropdown>
        </Nav>
    );
}

function logout(){

}

export default Receptionist;
