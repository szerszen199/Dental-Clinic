import React from "react";
import "./Patient.css";
import Nav from "react-bootstrap/Nav";
import {NavDropdown} from "react-bootstrap";
import "react-custom-flag-select/lib/react-custom-flag-select.min.css";
import {logout} from "../../../components/Login/Logout";
import {useTranslation} from "react-i18next";
import {Link} from "react-router-dom";
import MyAccount from "../Common/Common";

const Patient = () => {
    const {t} = useTranslation();

    return (
        <Nav activeKey={window.location.pathname}>
            <NavDropdown title={t("Appointments")} id="navbarScrollingDropdown">
                <Nav.Link to="/my-appointments">{t("My appointments")}</Nav.Link>
                <Nav.Link to="/plan-appointment">{t("Plan an appointment")}</Nav.Link>
                <NavDropdown.Divider/>
                <Nav.Link to="/list-doctors">{t("List of doctors")}</Nav.Link>
            </NavDropdown>
            <Nav.Link as={Link} to="/prescriptions">{t("My prescriptions")}</Nav.Link>
           <MyAccount/>
        </Nav>
    );
};

export default Patient;
