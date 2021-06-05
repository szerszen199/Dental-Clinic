import React from "react";
import "./Doctor.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import MyAccount from "../Common/Common";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";

function Doctor() {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.hash}>
            <NavDropdown title={t("Appointments")} id="navbarScrollingDropdown">
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu"
                          to="/my-appointments">{t("My appointments")}</Nav.Link>
                <NavDropdown.Divider/>
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu"
                          to="/list-doctors">{t("List of doctors")}</Nav.Link>
            </NavDropdown>
            <Nav.Link as={Link} to="/prescriptions">{t("Prescriptions")}</Nav.Link>
            <Nav.Link as={Link} to="/dashboard">{t("Documentation")}</Nav.Link>
            <MyAccount/>
        </Nav>
    );
}


export default Doctor;
