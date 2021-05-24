import React from "react";
import "./Patient.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {useTranslation} from "react-i18next";
import MyAccount from "../Common/Common";
import {Link} from "react-router-dom";

const Patient = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title={t("Appointments")} id="navbarScrollingDropdown">
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu"
                          to="/my-appointments">{t("My appointments")}</Nav.Link>
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu"
                          to="/plan-appointment">{t("Plan an appointment")}</Nav.Link>
                <NavDropdown.Divider/>
                <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} className="navMenu"
                          to="/list-doctors">{t("List of doctors")}</Nav.Link>
            </NavDropdown>
            <Nav.Link as={Link} style={{color: "rgb(127, 127, 127)"}} to="/prescriptions">{t("My prescriptions")}</Nav.Link>
            <MyAccount/>
        </Nav>
    );
};

export default Patient;
