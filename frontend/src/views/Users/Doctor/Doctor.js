import React from "react";
import "./Doctor.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import MyAccount from "../Common/Common";
import {useTranslation} from "react-i18next";


function Doctor() {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title="Appointments" id="navbarScrollingDropdown">
                <Nav.Link to="/my-appointments">{t("My appointments")}</Nav.Link>
                <NavDropdown.Divider/>
                <Nav.Link to="/list-doctors">{t("List of doctors")}</Nav.Link>
            </NavDropdown>
            <Nav.Link to="/prescriptions">{t("Prescriptions")}</Nav.Link>
            <Nav.Link to="/dashboard">{t("Documentation")}</Nav.Link>
            <MyAccount/>
        </Nav>
    );
}


export default Doctor;
