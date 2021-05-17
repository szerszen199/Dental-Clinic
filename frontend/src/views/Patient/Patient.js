import React from "react";
import "./Patient.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";

function Patient() {

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title="Appointments" id="navbarScrollingDropdown">
                <NavDropdown.Item>
                    <LinkContainer to="/my-appointments">
                        <Nav.Link>My appointments</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
                <NavDropdown.Item>
                    <LinkContainer to="/plan-appointment">
                        <Nav.Link>Plan an appointment</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item>
                    <LinkContainer to="/list-doctors">
                        <Nav.Link>List of doctors</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
            </NavDropdown>
            <LinkContainer to="/prescriptions">
                <Nav.Link>Prescriptions</Nav.Link>
            </LinkContainer>
            <LinkContainer to="/account">
                <Nav.Link>My Account</Nav.Link>
            </LinkContainer>
        </Nav>
    );
}


export default Patient;
