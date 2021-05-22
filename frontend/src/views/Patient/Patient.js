import React from "react";
import "./Patient.css";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from "react-router-bootstrap";
import {NavDropdown} from "react- ";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {logout} from "../../components/Login/Logout";
import {useTranslation} from "react-i18next";

const Patient = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title="Appointments" id="navbarScrollingDropdown">
                <NavDropdown.Item>
                    <LinkContainer to="/my-appointments">
                        <Nav.Link>{t("My appointments")}</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
                <NavDropdown.Item>
                    <LinkContainer to="/plan-appointment">
                        <Nav.Link>{t("Plan an appointment")}</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
                <NavDropdown.Divider/>
                <NavDropdown.Item>
                    <LinkContainer to="/list-doctors">
                        <Nav.Link>{t("List of doctors")}</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
            </NavDropdown>
            <LinkContainer to="/prescriptions">
                <Nav.Link>{t("My prescriptions")}</Nav.Link>
            </LinkContainer>
            <NavDropdown title="My Account" id="navbarScrollingDropdown">
                <NavDropdown.Item>
                    <LinkContainer to="/account">
                        <Nav.Link>{t("Edit My Account")}</Nav.Link>
                    </LinkContainer>
                </NavDropdown.Item>
                <NavDropdown.Item>
                    <span onClick={logout} style={{paddingLeft: "8px", color: "rgb(127, 127, 127)"}}>{t("Logout")}</span>
                </NavDropdown.Item>
            </NavDropdown>
        </Nav>
    );
};

export default Patient;
