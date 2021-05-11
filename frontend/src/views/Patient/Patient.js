import React, {useState} from "react";
import Navbar from "react-bootstrap/Navbar";
import "./Patient.css";
import Routes from "../../router/Routes";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import DarkModeToggle from "react-dark-mode-toggle";
import Switch from "react-switch";
import {DarkModeSwitch} from 'react-toggle-dark-mode';


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
                    {/*<Switch*/}
                    {/*    onChange={(checked: boolean) => {*/}
                    {/*        this.a = checked*/}
                    {/*    }}*/}
                    {/*     checked/>*/}
                    {/*<DarkModeToggle*/}
                    {/*    style={{ paddingTop: '5rem' }}*/}
                    {/*    onChange={setIsDarkMode}*/}
                    {/*    checked={isDarkMode}*/}
                    {/*    size={50}*/}
                    {/*/>*/}
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
