import React from "react";
import Navbar from "react-bootstrap/Navbar";
import "./App.css";
import Routes from "./router/Routes";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";

function App() {
    if (false === true) {
        return (
            <div className="App container py-3">
                <Navbar collapseOnSelect bg="light" expand="md" className="mb-3">
                    <LinkContainer to="/">
                        <Navbar.Brand className="font-weight-bold text-muted">
                            Home
                        </Navbar.Brand>
                    </LinkContainer>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav activeKey={window.location.pathname}>
                            <LinkContainer to="/register">
                                <Nav.Link>Register</Nav.Link>
                            </LinkContainer>
                            <LinkContainer to="/login">
                                <Nav.Link>Login</Nav.Link>
                            </LinkContainer>
                            <LinkContainer to="/accounts">
                                <Nav.Link>Accounts</Nav.Link>
                            </LinkContainer>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <Routes/>
            </div>
        );
    } else {
        return (
            <div className="App container py-3">
                <Navbar collapseOnSelect bg="light" expand="md" className="mb-3">
                    <LinkContainer to="/dashboard">
                        <Navbar.Brand className="font-weight-bold text-muted">
                            Home
                        </Navbar.Brand>
                    </LinkContainer>
                    <Navbar.Toggle/>
                    <Navbar.Collapse className="justify-content-end">
                        <Nav activeKey={window.location.pathname}>
                            <LinkContainer to="/appointments">
                                <Nav.Link>Appointments</Nav.Link>
                            </LinkContainer>
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
                    </Navbar.Collapse>
                </Navbar>
                <Routes/>
            </div>
        );
    }
}

export default App;
