import React, {useState} from "react";
import Navbar from "react-bootstrap/Navbar";
import "./Guest.css";
import { useLocation } from "react-router-dom";
import Routes from "../../router/Routes";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {DarkModeSwitch} from 'react-toggle-dark-mode';
import BreadCrumbs from "../../components/Breadcrumbs/Breadcrumbs";

function Guest() {
    const [isDarkMode, setIsDarkMode] = useState(() => false);
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
            <BreadCrumbs/>
            <Routes/>
        </div>
    );
}


export default Guest;
