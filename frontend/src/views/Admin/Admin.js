import React from "react";
import "./Admin.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {NavDropdown} from "react-bootstrap";
import {logout} from "../../components/Login/Logout";


function Admin() {
    return (
        <Nav activeKey={window.location.pathname}>
            <LinkContainer to="/accounts">
                <Nav.Link>User's Accounts</Nav.Link>
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



export default Admin;
