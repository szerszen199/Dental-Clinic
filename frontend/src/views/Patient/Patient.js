import React, {useState} from "react";
import Navbar from "react-bootstrap/Navbar";
import "./Patient.css";
import Routes from "../../router/Routes";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {DarkModeSwitch} from 'react-toggle-dark-mode';
import {NavDropdown} from "react-bootstrap";


function Patient() {
    const [isDarkMode, setIsDarkMode] = useState(() => false);
    return (
        <div className="App container py-3 ">
            <Navbar collapseOnSelect bg="light" expand="md" className="shadow-box-example mb-3">
                <LinkContainer to="/dashboard">
                    <Navbar.Brand className="font-weight-bold text-muted">
                        Home
                    </Navbar.Brand>
                </LinkContainer>
                <Navbar.Toggle/>
                <Navbar.Collapse className="justify-content-end">
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
                            <Nav.Link>Account</Nav.Link>
                        </LinkContainer>
                        <LinkContainer to="/accounts">
                            <Nav.Link>Accounts</Nav.Link>
                        </LinkContainer>
                    </Nav>
                    <DarkModeSwitch
                        style={{marginLeft: '1rem'}}
                        checked={isDarkMode}
                        onChange={setIsDarkMode}
                        size={30}
                        sunColor={"#FFDF37"}
                        moonColor={"#bfbfbb"}
                    />
                </Navbar.Collapse>
            </Navbar>
            <Routes/>
        </div>
    );
}

export default Patient;
